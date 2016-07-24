package com.shopnosoft.earthquakeresponsesystem;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity_002 extends AppCompatActivity {

    UserLocalStore userLocalStore;
    //for Double back press
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity_01);

        final EditText etusernamelgn = (EditText) findViewById(R.id.etUsernamelogin);
        final EditText etpasswordlgn = (EditText) findViewById(R.id.etPasswordlogin);
        final Button bLoginlgn = (Button) findViewById(R.id.bLoginlgn);
        final TextView registerlink = (TextView) findViewById(R.id.tvRegisterHere);


        userLocalStore = new UserLocalStore(this);
        // userLocalStore.setUserLoggedIn(false);

        registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                Intent registerIntent = new Intent(LoginActivity_002.this, RegisterActivity_000.class);
                LoginActivity_002.this.startActivity(registerIntent);
                finish();

            }
        });


        bLoginlgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etusernamelgn.getText().toString();
                final String password = etpasswordlgn.getText().toString();


                if(username.isEmpty() || password.isEmpty()){

                    Toast.makeText(getBaseContext(), "Please write Username and Password",Toast.LENGTH_SHORT).show();


                }
                else{

                    Response.Listener<String> responseListner = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonresponse = new JSONObject(response);
                                boolean success = jsonresponse.getBoolean("success");

                                if(success){
                                    String lgnname = jsonresponse.getString("name");
                                    String lgnemail = jsonresponse.getString("email");
                                    String lgnusername = jsonresponse.getString("username");
                                    String lgnpassword = jsonresponse.getString("password");
                                    String lgnmobileno = jsonresponse.getString("password");

////////////////////////Storing USer Data Locally/////////////////////////////////////////////

                                    User user = new User (lgnname,lgnemail,lgnusername,lgnpassword,lgnmobileno);
                                    userLocalStore.storeUserData(user);
                                    userLocalStore.setUserLoggedIn(true);

                                    //////////////////////Storing USer Data Locally/ ends////////////////////////////////////

                                    Intent intent = new Intent(LoginActivity_002.this, LogoutActivity_004.class);
                                    intent.putExtra("name",lgnname);
                                    intent.putExtra("username",username);
                                    intent.putExtra("email",lgnemail);
                                    LoginActivity_002.this.startActivity(intent);
                                    finish();

                                }
                                else{

                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity_002.this);
                                    builder.setMessage("Login Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest_003 loginRequest = new LoginRequest_003(username,password,responseListner);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity_002.this);
                    queue.add(loginRequest);
                }

            }
        });





    }

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
