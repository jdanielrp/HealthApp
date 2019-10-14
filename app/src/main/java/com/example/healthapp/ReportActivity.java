package com.example.healthapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReportActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.btnDate) Button _btnDate;
    @BindView(R.id.btnDateFinal) Button _btnDateFinal;
    @BindView(R.id.tvSelectedDate) TextView date;
    @BindView(R.id.tvSelectedDateFinal) TextView dateFinal;
    @BindView(R.id.Filtros) LinearLayout linear;
    @BindView(R.id.checkboxFiltros) CheckBox checkBox;
    @BindView(R.id.generarReporte) Button generarReporte;
    @BindView(R.id.spinnerReportes) Spinner spinnerReportes;
    DatePickerDialog datePickerDialog;
    String nombreReporte = null;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        ButterKnife.bind(this);
        linear.setVisibility(View.INVISIBLE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linear.setVisibility(View.VISIBLE);
                } else {
                    linear.setVisibility(View.INVISIBLE);
                }
            }
        });
        _btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(ReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        _btnDateFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(ReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateFinal.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        generarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "";

                if (spinnerReportes.getSelectedItem().toString().equals("-Seleccione Tipo de Reporte-")) {
                    message = "Seleccione tipo de reporte";
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    if (checkBox.isChecked()) {
                        if (date.getText().length() == 0 || dateFinal.getText().length() == 0 ) {
                            message = "Seleccione fecha inicial y fecha final";
                            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return;
                        }
                    }
                    //generate PDF
                    generarReporte(spinnerReportes.getSelectedItem().toString());
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reportes");
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.nuevoPaciente)
        {
            //Do something Like starting an activity
            Intent intent = new Intent(this, PatientActivity.class);
            startActivity(intent);

        }
        else if(view.getId() == R.id.medicamento)
        {
            //Do something Like starting an activity
            Intent intent = new Intent(this, DrugsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void generarReporte (String reporte) {
        nombreReporte = reporte;
        if (isWriteStoragePermissionGranted()) {
            downloadPDF();
        }
    }

    public void downloadPDF () {
        try {
            Connection conn = DbConnection.getConnection();
            if (conn == null) {

            } else {
                String query = FileUtils.getNombreReporte(nombreReporte);

                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(query);
                mContext = getApplicationContext();
                String dest = FileUtils.getAppPath(mContext) + nombreReporte + ".pdf";
                /* Creating Document */
                Document document = new Document();
                if (new File(dest).exists()) {
                    new File(dest).delete();
                }

                // Location to save
                PdfWriter.getInstance(document, new FileOutputStream(dest));

                // Open to write
                document.open();

                // Document Settings
                document.setPageSize(PageSize.LETTER);
                document.addCreationDate();
                document.addAuthor("Daniel Rivera");
                document.addCreator("Daniel Rivera");

                /**
                 * How to USE FONT....
                 */
                BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                // LINE SEPARATOR
                LineSeparator lineSeparator = new LineSeparator();
                lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

                // Title Order Details...
                // Adding Title....
                Font mOrderDetailsTitleFont = new Font(urName, 36.0f, Font.NORMAL, BaseColor.BLACK);
                Chunk mOrderDetailsTitleChunk = new Chunk("Reporte CiruBari", mOrderDetailsTitleFont);
                Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
                mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
                document.add(mOrderDetailsTitleParagraph);

                // Adding Report Name....
                mOrderDetailsTitleFont = new Font(urName, 30.0f, Font.NORMAL, BaseColor.BLACK);
                mOrderDetailsTitleChunk = new Chunk(nombreReporte, mOrderDetailsTitleFont);
                Paragraph mReportNameParagraph = new Paragraph(mOrderDetailsTitleChunk);
                mReportNameParagraph.setAlignment(Element.ALIGN_CENTER);
                document.add(mReportNameParagraph);

                // add a couple of blank lines
                document.add( Chunk.NEWLINE );

                //we have two columns in our table
                PdfPTable LogTable = new PdfPTable(2);
                //create a cell object
                PdfPCell table_cell;
                while (rs.next()) {
                    String columna = rs.getString("columna");
                    table_cell = new PdfPCell(new Phrase(columna));
                    LogTable.addCell(table_cell);
                    String total = rs.getString("total");
                    table_cell = new PdfPCell(new Phrase(total));
                    LogTable.addCell(table_cell);
                }
                /* Attach report table to PDF */
                document.add(LogTable);
                document.close();
                Toast.makeText(mContext, "PDF Created", Toast.LENGTH_SHORT).show();
                /* Open Generated PDF Document */
                FileUtils.openFile(mContext, new File(dest));
                /* Close all DB related objects */
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getAppPath(Context context) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory()
                + File.separator
                + context.getResources().getString(R.string.app_name)
                + File.separator);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getPath() + File.separator;
    }


    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //resume tasks needing this permission
                    downloadPDF();
                } else {
                    //progress.dismiss();
                }
                break;

            case 3:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //resume tasks needing this permission
                    //SharePdfFile();
                } else {
                    //progress.dismiss();
                }
                break;
        }
    }

}