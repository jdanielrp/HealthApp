package com.example.healthapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.healthapp.R;

public class CalculatorDosisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_dosis);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Calculadoras");
    }
}
