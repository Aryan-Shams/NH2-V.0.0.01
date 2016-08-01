package com.shopnosoft.earthquakeresponsesystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile_Activity extends AppCompatActivity {


    //for Double back press
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    TextView upfl_wlcome,upfl_name,upfl_username,upfl_email,upfl_mobileno,upfl_usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        upfl_wlcome = (TextView)findViewById(R.id.UsrpflWlcm);
        upfl_name = (TextView)findViewById(R.id.usrpfl_Name);
        upfl_username = (TextView)findViewById(R.id.usrpfl_usrName);
        upfl_email = (TextView)findViewById(R.id.usrpfl_email);
        upfl_mobileno = (TextView)findViewById(R.id.usrpfl_mblno);
        upfl_usertype = (TextView)findViewById(R.id.usrpfl_usrtyp);


        showuserpfl();

    }

    @Override
    protected void onStart() {
        super.onStart();

        showuserpfl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showuserpfl();

    }


    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {

            Intent intent = new Intent(UserProfile_Activity.this, Navigation_Activity_000.class);
            UserProfile_Activity.this.startActivity(intent);
            finish();
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

    }

    private void showuserpfl(){



        String usrpflfullname,usrpflmail,usrpflusrname,usrpflmbl,usrpflusrtyp;


        /////////////////////////////////
        SharedPreferences sharedpref_usr = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        usrpflfullname=sharedpref_usr.getString("Name_Key","");
        usrpflmail= sharedpref_usr.getString("Email_Key","");
        usrpflusrname=sharedpref_usr.getString("UserName_Key","");
        usrpflmbl=sharedpref_usr.getString("MobileNo_Key","");
        usrpflusrtyp=sharedpref_usr.getString("UserType_Key","");

        upfl_wlcome.setText(usrpflusrname+"'"+"s Profile :");
        upfl_name.setText(usrpflfullname);
        upfl_username.setText(usrpflusrname);
        upfl_email.setText(usrpflmail);
        upfl_mobileno.setText(usrpflmbl);
        upfl_usertype.setText(usrpflusrtyp);


    }
}
