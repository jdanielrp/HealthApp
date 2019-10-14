package com.example.healthapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.LineNumberInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DrugsActivity extends AppCompatActivity implements View.OnClickListener{
    final DatabaseHelper dbHelper = new DatabaseHelper(this);
    //@BindView(R.id.inputDrugSearch) EditText inputDrugSearch;
    //EditText inputDrugSearch = (EditText) findViewById(R.id.inputDrugSearch);
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_drugs);
        getListView();
        //Put a listener to edit text search
        EditText etSearch = (EditText) findViewById(R.id.inputDrugSearch);
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.getFilter().filter(cs);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dosis");
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.nuevoPaciente)
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


    private String getListView() {
        String z = null;
        try {
            Connection con = DbConnection.getConnection();
            if (con == null) {
                z = "Check Internet Access";
            } else {
                String query = "select * from dbo.MEDICAMENTO";
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(query);
                int index = 0;
                rs.last();
                int resnum = rs.getRow();
                String[] values = new String[resnum]; //total number of rows
                rs.beforeFirst();
                while(rs.next()){
                    values[index] = rs.getString(2);
                    index++;
                }
                ListView list = findViewById(R.id.list_view);
                adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    }
                });
            }
        } catch (SQLException e) {
            z = e.getMessage();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return z;
    }
}