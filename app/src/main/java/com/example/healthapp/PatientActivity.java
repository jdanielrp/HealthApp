package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;


public class PatientActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.btnPatientCancel) Button _btnPatientCancel;
    @BindView(R.id.btnPatientSubmit) Button _btnPatientSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
        ButterKnife.bind(this);
        _btnPatientCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancelPatient();
            }
        });
        _btnPatientSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                submitPatient();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nuevo Paciente");
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

    public void cancelPatient() {
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }

    public void submitPatient() {
        Intent intent = new Intent(getApplicationContext(), RiskFormActivity.class);
        startActivity(intent);
    }
}