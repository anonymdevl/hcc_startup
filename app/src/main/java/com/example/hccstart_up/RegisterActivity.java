package com.example.hccstart_up;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements  View.OnClickListener{
    private EditText etName, et_team_name, etEmail, etPassword, etReenterPassword;
    private TextView tvStatus;
    private TextView tv_register;
    private Button btnSubmit;
    private String URL = "http://10.0.2.2/hccstartup/register.php";
    private String name, t_name, email, password, reenterPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = findViewById(R.id.et_fullname_register);
        et_team_name = findViewById(R.id.et_team_name_register);
        etEmail = findViewById(R.id.et_email_register);
        etPassword = findViewById(R.id.et_password_register);
        etReenterPassword = findViewById(R.id.et_password_confirm_register);
        tvStatus = findViewById(R.id.tvStatus);
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        tv_register = findViewById(R.id.tv_already_registered);
        name = t_name = email = password = reenterPassword = "";

       /* btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

        });
*/
    }


    @Override
    public void onClick(View view) {

        name = etName.getText().toString().trim();
        t_name = et_team_name.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        reenterPassword = etReenterPassword.getText().toString().trim();

        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
        finish();

        if(!password.equals(reenterPassword)){
            Toast.makeText(RegisterActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
        }
        else if(!name.equals("") && !t_name.equals("") && !email.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.i ("res", response);
                    //Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    if (response.equals("success")) {
                        tvStatus.setText("Successfully registered.");
                        btnSubmit.setClickable(false);
                    } else if (response.equals("failure")) {
                        tvStatus.setText("Something went wrong!");                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("DEBUG","Request error");
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("full_name", name);
                    data.put("team_name", t_name);
                    data.put("email", email);
                    data.put("password", password);
                    Log.d("DEBUG DATA",data.toString());
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
            Log.d("DEBUG","Request attempted");
        }
    }

    //Links Registration Page to Login Page
    public void registered(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}