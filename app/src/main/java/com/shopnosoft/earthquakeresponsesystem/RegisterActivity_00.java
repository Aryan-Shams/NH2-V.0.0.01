package com.shopnosoft.earthquakeresponsesystem;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ResponseCache;

public class RegisterActivity_00 extends AppCompatActivity {
    //for Double back press
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity_00);


//Initializing the veriables

        final EditText etfullnamereg = (EditText) findViewById(R.id.etnamereg);
        final EditText etmailreg = (EditText) findViewById(R.id.etemailreg);
        final EditText etphonenoreg = (EditText) findViewById(R.id.etphnnoreg);
        final EditText etusernamereg = (EditText) findViewById(R.id.etusrnamereg);
        final EditText etpasswordreg = (EditText) findViewById(R.id.etpasswordreg);
        final EditText etrepasswordreg = (EditText) findViewById(R.id.etrepasswordreg);


        final Button bRegisterinreg = (Button) findViewById(R.id.bRegisterreg);

        bRegisterinreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//Getting Data from the fields

                final String name=etfullnamereg.getText().toString();
                final String email=etmailreg.getText().toString();
                final String mobileno=etphonenoreg.getText().toString();
                final String username=etusernamereg.getText().toString();
                final String password=etpasswordreg.getText().toString();

                Response.Listener<String> responseListner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

////////////////////////Storing USer Data Locally/////////////////////////////////////////////
                           /*     User registeredData = new User (name,email,username,password,mobileno);
                                userLocalStore.storeUserData(registeredData);
                                userLocalStore.setUserLoggedIn(true);
                                */
//////////////////////Storing USer Data Locally/ ends////////////////////////////////////

                                /*
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);

                                */
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity_00.this);
                                builder.setMessage("User Already Exists!!! Resistration  Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest_001 registerRequest = new RegisterRequest_001 (name,email,username,password,mobileno,responseListner );
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity_00.this);
                queue.add(registerRequest);




            }
        });
    }



    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

    }


}
