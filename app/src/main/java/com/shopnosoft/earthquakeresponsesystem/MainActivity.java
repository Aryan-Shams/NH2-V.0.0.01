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
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    private WifiManager wfm;
    private ConnectivityManager cntm;
    // GPSTracker class
    GPSTracker gps;

    Context context;
    //For Address
    private RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        requestQueue = Volley.newRequestQueue(this);


        //
        if(!network() ){
            Toast.makeText(getBaseContext(), "No network!",
                    Toast.LENGTH_SHORT).show();
            createNetErrorDialog();
        }
        else if(!isLocationServiceEnabled()){
            Toast.makeText(getBaseContext(), "No GPRS!",
                    Toast.LENGTH_SHORT).show();
            createNetErrorDialog();
        }


        else
        {

            if(authenticate() == false) {

                Intent intent = new Intent(MainActivity.this, LoginActivity_002.class);
                MainActivity.this.startActivity(intent);
                finish();
            }

            else{


              //  Intent intent = new Intent(MainActivity.this, LogoutActivity_004.class);
                Intent intent = new Intent(MainActivity.this, Navigation_Activity_000.class);

                MainActivity.this.startActivity(intent);
                finish();

            }
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        if(!network() ){
            Toast.makeText(getBaseContext(), "No network!",
                    Toast.LENGTH_SHORT).show();
            createNetErrorDialog();
        }
        else if(!isLocationServiceEnabled()){
            Toast.makeText(getBaseContext(), "No GPRS!",
                    Toast.LENGTH_SHORT).show();
            createNetErrorDialog();
        }

        else
        {
            if(authenticate() == false) {

                Intent intent = new Intent(MainActivity.this, LoginActivity_002.class);
                MainActivity.this.startActivity(intent);
                finish();
            }

            else{
                //  Intent intent = new Intent(MainActivity.this, LogoutActivity_004.class);
                Intent intent = new Intent(MainActivity.this, Navigation_Activity_000.class);

                MainActivity.this.startActivity(intent);
                finish();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        if(!network() ){

            createNetErrorDialog();
        }
        else if(!isLocationServiceEnabled()){
            createNetErrorDialog();
        }

        else
        {

            if(authenticate() == false) {

                Intent intent = new Intent(MainActivity.this, LoginActivity_002.class);
                MainActivity.this.startActivity(intent);
                finish();
            }

            else{
                //  Intent intent = new Intent(MainActivity.this, LogoutActivity_004.class);
                Intent intent = new Intent(MainActivity.this, Navigation_Activity_000.class);

                MainActivity.this.startActivity(intent);
                finish();

            }
        }

    }



////////<<-------------------------------CHECKING NETWORK CONNECTION STATUS------------------------->>///////////////////////

    public boolean network() {

//Get the wifi service of device
        wfm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
//Get the Mobile Dtata service of device
        cntm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo nInfo = cntm.getActiveNetworkInfo();

        if ((wfm.isWifiEnabled()) || (nInfo != null && nInfo.isConnected())) {
            return true;
        }

        else{
            return false;
        }

    }

////////<<-------------------------------CHECKING NETWORK CONNECTION STATUS ENDS-------------------->>///////////////////////

////////<<-------------------------------CHECKING GPS  STATUS Starts-------------------->>///////////////////////

public boolean isLocationServiceEnabled(){

        LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //GPS enabled
            return true;
        }

        else{
            //GPS disabled
            return false;
        }
    }


    ////////<<-------------------------------CHECKING GPS  STATUS Ends-------------------->>///////////////////////


///////<<-------------------------------CREATING ERROR DIALOG--------------------------------------->>////////////////////////

    protected void createNetErrorDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need a network connection and GPS to use this application. Please turn on mobile network or Wi-Fi and Enable Location in Settings.")
                .setTitle("Unable to connect")
                .setCancelable(false)
                .setPositiveButton("Enable",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_SETTINGS);

                                //startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));

                                startActivity(i);
                                //finish();
                            }
                        }
                )  .setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }
        )

        ;
        AlertDialog alert = builder.create();
        alert.show();
    }

///////<<-------------------------------CREATING ERROR DIALOG ENDS--------------------------------------->>///////////////////





///////<<-------------------------------Checking User Local Data--------------------------------------->>///////////////////


    private boolean authenticate(){

        SharedPreferences sharedpref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);


        String loggedin;

        loggedin = sharedpref.getString("loginStatus","");

        if (loggedin.equals("loggedin")) {

            return true;
        }

        return false;
    }

    ///////<<-------------------------------Checking User Local Data Ends--------------------------------------->>///////////////////



}
