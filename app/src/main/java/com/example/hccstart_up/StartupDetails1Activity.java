package com.example.hccstart_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class StartupDetails1Activity extends AppCompatActivity implements View.OnClickListener{
    EditText startup_Name, b_Description, number_of_Members, member_Names;
    Button next;
    TextView tv_status;
    private String URL = "http://10.0.2.2/hccstartup/startup_registration_details_1.php";
    private String startupName, description, number_of_members, member_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_details_1);

        startup_Name = (EditText) findViewById(R.id.et_startup_name);
        b_Description = (EditText) findViewById(R.id.et_brief_description);
        number_of_Members = (EditText) findViewById(R.id.et_number_of_members);
        member_Names = (EditText) findViewById(R.id.et_member_names);
        /*mn_2 = (EditText) findViewById(R.id.et_mn_2);
        mn_3 = (EditText) findViewById(R.id.et_mn_3);
        mn_4 = (EditText) findViewById(R.id.et_mn_4);*/
        tv_status = (TextView) findViewById(R.id.tvStatus);

        next = (Button) findViewById(R.id.btn_next);

        startupName = description = number_of_members = member_names = "";

        //Links the 1st startup details page to the 2nd startup details page.
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startupName = startup_Name.getText().toString().trim();
                description = b_Description.getText().toString().trim();
                number_of_members = number_of_Members.getText().toString().trim();
                member_names = member_Names.getText().toString().trim();
                /*mn2 = mn_2.getText().toString().trim();
                mn3 = mn_3.getText().toString().trim();
                mn4 = mn_4.getText().toString().trim();*/

                Intent i = new Intent(StartupDetails1Activity.this, StartupDetails2Activity.class);
                startActivity(i);
                finish();

                if(!startupName.equals("") && !description.equals("") && !number_of_members.equals("") && !member_names.equals("")){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.i ("res", response);
                            //Toast.makeText(StartupDetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                            if (response.equals("success")) {
                                tv_status.setText("Successfully uploaded.");
                                Log.d("DEBUG", "Button Responding");
                                next.setClickable(false);
                            } else if (response.equals("failure")) {
                                tv_status.setText("Something went wrong!");                    }
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
                            data.put("startupname", startupName);
                            data.put("description", description);
                            data.put("number_of_members", number_of_members);
                            data.put("member_names", member_names);
                            /*data.put("member_names", mn2);
                            data.put("member_names", mn3);
                            data.put("member_names", mn4);*/
                            Log.d("DEBUG DATA",data.toString());
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                    Log.d("DEBUG","Request attempted");
                }

                /*Intent i = new Intent(StartupDetails1Activity.this, StartupDetails2Activity.class);
                startActivity(i);*/
            }
        });

    }

    //Database Connection
    public void onClick(View view) {
    }
}