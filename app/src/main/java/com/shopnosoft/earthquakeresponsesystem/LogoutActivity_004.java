package com.shopnosoft.earthquakeresponsesystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LogoutActivity_004 extends AppCompatActivity {




    EditText etUsernameview,etemialview;
    TextView tvWelcomeMessage,tvlong_and_latt,co_to_addrs;

    Button logoutButton;

    //for Double back press
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;


private static String getLattitude = null, getLongititude = null,getaddress_frm_coordnt=null;


    ////////////////////////////////////////

    TextView coordinate,address;
    public static String Lattitude = null,Longitude = null;

    private WifiManager wfm;
    private ConnectivityManager cntm;
    // GPSTracker class
    GPSTracker gps;
    Context context;


    //For Address
    private RequestQueue requestQueue;
    ///////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_activity_004);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvlong_and_latt = (TextView) findViewById(R.id.lattiitude_view);
        co_to_addrs = (TextView) findViewById(R.id.cordnt_to_addrs);


        logoutButton = (Button) findViewById(R.id.btLogout);





        displayUserDetailspfl();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedpref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpref.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(LogoutActivity_004.this, LoginActivity_002.class);
                LogoutActivity_004.this.startActivity(intent);
                finish();
            }
        });

    }




    @Override
    protected void onRestart() {
        super.onRestart();

            displayUserDetailspfl();
           // Location();
          //  LocationToAddress();

    }




    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {

            Intent intent = new Intent(LogoutActivity_004.this, Navigation_Activity_000.class);
            LogoutActivity_004.this.startActivity(intent);
            finish();
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

    }



///////<<-------------------------------CREATING ERROR DIALOG ENDS--------------------------------------->>///////////////////

    private void displayUserDetailspfl(){

        String prflmsg,pflfullname,pflmail,pflusrname,pflpass,pflmbl,pflusrtyp,pflsts;


        /////////////////////////////////
        SharedPreferences sharedpref_usr = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        pflfullname=sharedpref_usr.getString("Name_Key","");
        pflmail= sharedpref_usr.getString("Email_Key","");
        pflusrname=sharedpref_usr.getString("UserName_Key","");
        pflpass=sharedpref_usr.getString("Password_Key","");
        pflmbl=sharedpref_usr.getString("MobileNo_Key","");
        pflusrtyp=sharedpref_usr.getString("UserType_Key","");
        pflsts=sharedpref_usr.getString("loginStatus","");







    }




}
