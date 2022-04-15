package com.example.hccstart_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static String URL_REGIST = "http://10.0.2.2/hccstartup/login.php";
    EditText email,password;
    MaterialButton login;
    TextView gotoreg;

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.loginbtn);
        gotoreg = findViewById(R.id.tv_gotoreg);
        gotoreg.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(i);
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                process_login_form();

            }
        });



    }
    private void process_login_form(){
        if(!validateEmail() | !validatePassword()){
            return;
        }

        String val_email = email.getText().toString();
        String val_password = password.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respObj = new JSONObject(response);
                            Boolean status = respObj.getBoolean("status");
                            String msg = respObj.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",val_email);
                params.put("password",val_password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private boolean validateEmail(){
        String val = email.getText().toString();
        if (val.isEmpty()) {
            email.setError("email required");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }


    private boolean validatePassword(){
        String val = password.getText().toString();
        String password_pattern =  "(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=])(?=\\S+$)(.{4,})$";

        if(val.isEmpty()){
            password.setError("Password is required!");
            return false;
        }
        else if(!val.matches(password_pattern)){
            password.setError("Your Password is not strong");
            return  false;
        }
        else{
            password.setError(null);
            return  true;
        }
    }
}