package com.example.hccstart_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText email, full_name,team_name,enter_password,confirm_password;
    Button submit;
    TextView already_Registered;

    private static String URL_REGIST = "http://10.0.2.2/hccstartup/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

            full_name = (EditText) findViewById(R.id.et_fullname_register);
            team_name = (EditText) findViewById(R.id.et_team_name_register);
            email = (EditText) findViewById(R.id.et_email_register);
            enter_password = (EditText) findViewById(R.id.et_password_register);
            confirm_password = (EditText) findViewById(R.id.et_password_confirm_register);
            submit = (Button) findViewById(R.id.btn_submit);
            already_Registered = (TextView) findViewById(R.id.tv_already_registered);

        already_Registered.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(i);

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process_register_form();

                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
    private void process_register_form(){
        if(!validateEmail() | !validatePassword()| !validatefullname() | !validateteamname()){
            return;
        }

        String val_email = email.getText().toString();
        String val_fname = full_name.getText().toString();
        String val_tname = team_name.getText().toString();
        String val_password = enter_password.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respObj = new JSONObject(response);
                            Boolean status = respObj.getBoolean("status");
                            String msg = respObj.getString("message");
                            if(status){

                                Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();


                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("full_name",val_fname);
                params.put("team_name",val_tname);
                params.put("email",val_email);
                params.put("password",val_password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private boolean validatefullname(){
        String val = full_name.getText().toString();
        if (val.isEmpty()) {
            full_name.setError("Full name required");
            return false;
        } else if (val.length() >= 50) {
            full_name.setError("Full name is too long");
            return false;
        } else {
            full_name.setError(null);
            return true;
        }

    } private boolean validateteamname(){
        String val = team_name.getText().toString();
        if (val.isEmpty()) {
            team_name.setError("Team name required");
            return false;
        }else {
                team_name.setError(null);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = email.getText().toString();
        String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            email.setError("Email is required!");
            return false;
        }
        else if(!val.matches(email_pattern)){
            email.setError("Your email is invalid");
            return  false;
        }
        else{
            email.setError(null);
            return  true;
        }
    }

    private boolean validatePassword(){
        String val = enter_password.getText().toString();
        String val_cp = confirm_password.getText().toString();
        String password_pattern =  "(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=])(?=\\S+$)(.{4,})$";

        if(val.isEmpty()){
            enter_password.setError("Password is required!");
            return false;
        }
        else if(!val.matches(password_pattern)){
            enter_password.setError("Your Password is not strong");
            return  false;
        }
        else if(!val_cp.matches(val)){
            confirm_password.setError("Password did not match");
            return  false;
        }
        else{
            enter_password.setError(null);
            return  true;
        }
    }
}