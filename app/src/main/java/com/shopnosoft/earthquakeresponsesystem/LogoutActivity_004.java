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


//Fields
        etUsernameview = (EditText) findViewById(R.id.etUsernameview);
        etemialview = (EditText) findViewById(R.id.etemialview);


        tvWelcomeMessage = (TextView) findViewById(R.id.tvWelcomeMessage);
        tvlong_and_latt = (TextView) findViewById(R.id.lattiitude_view);
        co_to_addrs = (TextView) findViewById(R.id.cordnt_to_addrs);


        logoutButton = (Button) findViewById(R.id.btLogout);



        requestQueue = Volley.newRequestQueue(this);



        displayUserDetailspfl();





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
        if(!network()){
            createNetErrorDialog();
        }
        else{
            displayUserDetailspfl();
           // Location();
          //  LocationToAddress();
        }
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
        builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.")
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





        SharedPreferences sharedpref_lcn = getSharedPreferences("UserLocation", Context.MODE_PRIVATE);

        getLattitude=sharedpref_lcn.getString("Lattitude_key","");
        getLongititude=sharedpref_lcn.getString("Longitude_key","");
        getaddress_frm_coordnt=sharedpref_lcn.getString("Location_to_address_Key","" );

        prflmsg = pflfullname + " Welcome to your user area"+pflpass+" "+pflmbl+" "+pflusrtyp+" "+pflsts+"";

        etUsernameview.setText(pflusrname);
        etemialview.setText(pflmail);
        tvWelcomeMessage.setText(prflmsg);



        String show_coordinates = "Lattitude : "+getLattitude+"\n Longitude : "+getLongititude+"";
        tvlong_and_latt.setText(show_coordinates);
        co_to_addrs.setText(getaddress_frm_coordnt);



    }




}
