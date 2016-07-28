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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity_000 extends AppCompatActivity {
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
                final String repassword=etrepasswordreg.getText().toString();
                final String usertype = "User";

                if(name.isEmpty() || email.isEmpty()|| mobileno.isEmpty()|| username.isEmpty()|| password.isEmpty() || repassword.isEmpty())
                {
                    Toast.makeText(getBaseContext(), "PleaseFill all the Fields",Toast.LENGTH_SHORT).show();

                }
                else{

                    if(password.equals(repassword)){


                        Response.Listener<String> responseListner = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {


                                        Intent intent = new Intent(RegisterActivity_000.this, LoginActivity_002.class);

                                        RegisterActivity_000.this.startActivity(intent);
                                        finish();

                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity_000.this);
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

                        RegisterRequest_001 registerRequest = new RegisterRequest_001 (name,email,username,password,mobileno,usertype,responseListner );
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity_000.this);
                        queue.add(registerRequest);



                    }
                    else{

                        Toast.makeText(getBaseContext(), "Password Doesn't match",Toast.LENGTH_SHORT).show();

                    }

                }


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
