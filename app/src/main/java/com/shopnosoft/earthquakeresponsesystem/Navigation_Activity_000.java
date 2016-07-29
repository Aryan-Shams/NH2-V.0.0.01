package com.shopnosoft.earthquakeresponsesystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Navigation_Activity_000 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private WifiManager wfm;
    private ConnectivityManager cntm;
    // GPSTracker class
    GPSTracker gps;
    //For Address
    private RequestQueue requestQueue;


    private static String getLattitude = null, getLongititude = null,getaddress_frm_coordnt=null,Lattitude = null,Longitude = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__activity_000);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        requestQueue = Volley.newRequestQueue(this);
        Location();
        LocationToAddress();
        storelocation();


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__activity_000, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.LogOut) {

            Intent intent = new Intent(Navigation_Activity_000.this, LogoutActivity_004.class);
            Navigation_Activity_000.this.startActivity(intent);
            finish();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }










public void showNoticeAlert(){

    Response.Listener<String> responseListner = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject jsonresponse = new JSONObject(response);
                boolean success = jsonresponse.getBoolean("success");

                if(success){


                    String rs_notice = jsonresponse.getString("notice");
                    String rs_status = jsonresponse.getString("status");
                    String rs_date = jsonresponse.getString("date");
                    String rs_time = jsonresponse.getString("time");

                    ////////////////////////Storing USer Data Locally/////////////////////////////////////////////


                    SharedPreferences sharedpref = getSharedPreferences("UserNotice", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString("Notice", rs_notice );
                    editor.putString("Status", rs_status );
                    editor.putString("Date", rs_date );
                    editor.putString("Time", rs_time );


                    editor.commit();


                    Toast.makeText(getBaseContext(), "DATA SAVED!",
                            Toast.LENGTH_SHORT).show();

                    //////////////////////Storing USer Data Locally/ ends////////////////////////////////////





                }
                else{

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Navigation_Activity_000.this);
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



    Navigation_Activity_000 shownotice = new Navigation_Activity_000(date,responseListner);
    RequestQueue queue = Volley.newRequestQueue(LoginActivity_002.this);
    queue.add(loginRequest);

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


    /////////////////////////////////<<-------------------------------- SHOWING LOCATION CO-ORDINATES  --------------------------->>////////////////////////
    public void Location(){

        gps = new GPSTracker(Navigation_Activity_000.this);

        double latitude,longitude;

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            if(latitude>0 && longitude>0){
                Lattitude = String.valueOf(latitude);
                Longitude = String.valueOf(longitude);
            }
            else{
                gps.canGetLocation();
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                Lattitude = String.valueOf(latitude);
                Longitude = String.valueOf(longitude);
            }

            SharedPreferences sharedpref_lcn = getSharedPreferences("UserLocation", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpref_lcn.edit();
            editor.putString("Lattitude_key", Lattitude );
            editor.putString("Longitude_key",Longitude);
            editor.commit();


        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();

        }


    }

////////////////////////////////////////////<<-------------------------------- SHOWING LOCATION CO-ORDINATES  ENDS----------------------------->>////////////////////////



////////////////////////////////////////////<<------------------------------------- Get Address From Location---------------------------------->>////////////////////////


    public void LocationToAddress(){

        gps = new GPSTracker(Navigation_Activity_000.this);


        double for_address_latitude = gps.getLatitude();
        double for_address_longitude = gps.getLongitude();

        final String lattoadd = String.valueOf(for_address_latitude);
        final String logtoadd = String.valueOf(for_address_longitude);

        JsonObjectRequest request = new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng="+lattoadd+","+logtoadd+"&key=AIzaSyBma_A78YGbZwGav3SR3vSGoAXka8FGFzQ", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String get_address = response.getJSONArray("results").getJSONObject(0).getString("formatted_address");


                    SharedPreferences sharedpref_lcn = getSharedPreferences("UserLocation",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpref_lcn.edit();
                    editor.putString("Location_to_address_Key", get_address );
                    editor.commit();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

////////////////////////////////////////////<<------------------------ Get Address From Location Ends------------------->>/////////////////////////////////////////////////////




    ////////////////////////////////////////////<<------------------------Store Location On DB------------------->>/////////////////////////////////////////////////////




    public void storelocation(){

        /////////////////////////////////
        SharedPreferences sharedpref_usr = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        final String strmobileno=sharedpref_usr.getString("MobileNo_Key","");

        SharedPreferences sharedpref_lcn = getSharedPreferences("UserLocation", Context.MODE_PRIVATE);

        final String strlattitude=sharedpref_lcn.getString("Lattitude_key","");
        final String strlongititude=sharedpref_lcn.getString("Longitude_key","");
        final String straddress=sharedpref_lcn.getString("Location_to_address_Key","" );



        Response.Listener<String> responseListner = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {


                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Navigation_Activity_000.this);
                        builder.setMessage("Data Update Unsuccessfull!!!!")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        LocationStore_Request locationstrRequest = new LocationStore_Request (strmobileno,strlattitude,strlongititude,straddress,responseListner );
        RequestQueue queue = Volley.newRequestQueue(Navigation_Activity_000.this);
        queue.add(locationstrRequest);



    }


}
