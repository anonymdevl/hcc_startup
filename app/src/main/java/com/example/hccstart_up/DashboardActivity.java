package com.example.hccstart_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{
Button btn_reg_startup, btn_pitch, btn_addteam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btn_reg_startup = findViewById(R.id.btn_register_for_startup);
        btn_pitch = findViewById(R.id.btn_pitch_idea);
        btn_addteam = findViewById(R.id.btn_add_team);

        btn_reg_startup.setOnClickListener(this);
        btn_pitch.setOnClickListener(this);
        btn_addteam.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_register_for_startup:
                Intent i = new Intent(DashboardActivity.this,StartupRegisterActivity.class);
                startActivity(i);
                break;
            case R.id.btn_pitch_idea:
                Intent intent = new Intent(DashboardActivity.this,PitchActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_add_team:
                Intent intent2 = new Intent(DashboardActivity.this,AddTeamActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}