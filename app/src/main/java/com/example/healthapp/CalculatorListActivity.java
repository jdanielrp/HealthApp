package com.example.healthapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CalculatorListActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_list);
        getListView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Calculadoras");
    }

    private String getListView() {
        String z = null;
        try {
            Connection con = DbConnection.getConnection();
            if (con == null) {
                z = "Check Internet Access";
            } else {
                String query = "select * from dbo.CALCULADORA";
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
                ListView list = findViewById(R.id.list_view_calculadoras);
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
