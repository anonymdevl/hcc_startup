package com.example.hccstart_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartupDetails2Activity extends AppCompatActivity {
    EditText faculty_name, faculty_email, me_1, me_2, me_3, me_4, industry_name;
    Button previous, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_details_2);

        faculty_name = (EditText) findViewById(R.id.et_faculty_name);
        faculty_email = (EditText) findViewById(R.id.et_faculty_email);
        me_1 = (EditText) findViewById(R.id.et_me_1);
        me_2 = (EditText) findViewById(R.id.et_me_2);
        me_3 = (EditText) findViewById(R.id.et_me_3);
        me_4 = (EditText) findViewById(R.id.et_me_4);
        industry_name = (EditText) findViewById(R.id.et_industry_name);

        submit = (Button) findViewById(R.id.btn_td_submit);
        previous = (Button) findViewById(R.id.btn_td_previous);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StartupDetails2Activity.this, DashboardActivity.class);
                startActivity(i);

                Toast.makeText(StartupDetails2Activity.this, "Details Submitted", Toast.LENGTH_SHORT).show();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StartupDetails2Activity.this, StartupDetails1Activity.class);
                startActivity(i);
            }
        });
    }
}