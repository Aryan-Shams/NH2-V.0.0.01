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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Navigation_Activity_000 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //for Double back press
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    private WifiManager wfm;
    private ConnectivityManager cntm;
    // GPSTracker class
    GPSTracker gps;
    //For Address
    private RequestQueue requestQueue;


    private static String getLattitude = null, getLongititude = null,getaddress_frm_coordnt=null,Lattitude = null,Longitude = null,Address=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__activity_000);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        requestQueue = Volley.newRequestQueue(this);


        final TextView tvdate = (TextView)findViewById(R.id.tv_date);
        final TextView tvstatuse = (TextView)findViewById(R.id.tvstatus);
        final TextView tvNotice = (TextView) findViewById(R.id.tvalrtnotice) ;

     //   final Button btSafe = (Button) findViewById(R.id.btnSafe);
       // final Button btHelp = (Button) findViewById(R.id.btnHelp);



  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

       final String gtntc,gtdt,gtsts,shnownotice;

      SharedPreferences sharedpref_alrt = getSharedPreferences("UserAlert",Context.MODE_PRIVATE);

        gtntc = sharedpref_alrt.getString("Alert_notice_Key","");
        gtsts = sharedpref_alrt.getString("Alert_status_Key","");
        gtdt=    sharedpref_alrt.getString("Alert_date_Key","");


        tvstatuse.setText(gtsts);

        tvdate.setText(gtdt);

       tvNotice.setText(gtntc);


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        showNoticeAlert();
        storelocation();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {

                Toast.makeText(getBaseContext(), "Press once again to exit!",
                        Toast.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        showNoticeAlert();
        storelocation();


    }

    @Override
    protected void onResume() {
        super.onResume();

        showNoticeAlert();
        storelocation();
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

        if (id == R.id.nav_Location) {
            Intent intent = new Intent(Navigation_Activity_000.this, Location_View.class);
            Navigation_Activity_000.this.startActivity(intent);


        } else if (id == R.id.nav_emergency) {
            Intent intent = new Intent(Navigation_Activity_000.this, EmergencyActivity.class);
            Navigation_Activity_000.this.startActivity(intent);

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(Navigation_Activity_000.this, UserProfile_Activity.class);
            Navigation_Activity_000.this.startActivity(intent);


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




    public void storelocation(){
        /////////////////////////////////<<-------------------------------- SHOWING LOCATION CO-ORDINATES  --------------------------->>////////////////////////

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


        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();

        }

/////////////////////////////////<<-------------------------------- SHOWING LOCATION CO-ORDINATES  --------------------------->>////////////////////////

////////////////////////////////////////////<<------------------------------------- Get Address From Location---------------------------------->>////////////////////////


        JsonObjectRequest addrequest = new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng="+Lattitude+","+Longitude+"&key=AIzaSyBma_A78YGbZwGav3SR3vSGoAXka8FGFzQ", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final String get_address = response.getJSONArray("results").getJSONObject(0).getString("formatted_address");

                    Address = get_address;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(addrequest);

////////////////////////////////////////////<<------------------------------------- Get Address From Location---------------------------------->>////////////////////////
        /////////////////////////////////
        SharedPreferences sharedpref_usr = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        final String strmobileno=sharedpref_usr.getString("MobileNo_Key","");


        Response.Listener<String> responseListner = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (!success) {
                        Toast.makeText(getBaseContext(), "Location Update Failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        LocationStore_Request locationstrRequest = new LocationStore_Request (strmobileno,Lattitude,Longitude,Address,responseListner );
        RequestQueue queue = Volley.newRequestQueue(Navigation_Activity_000.this);
        queue.add(locationstrRequest);

    }

    public void showNoticeAlert(){

        final String URL;
        URL = "http://androidlogin.shopnosoft.com/notice.php";

        JsonObjectRequest request = new JsonObjectRequest(URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    final String success = response.getString("success");

                    final String alrtnotice = response.getString("notice");
                    final String alrtstatus = response.getString("status");
                    final String alrtdate = response.getString("date");

             final TextView tvdate = (TextView)findViewById(R.id.tv_date);
                    final TextView tvstatuse = (TextView)findViewById(R.id.tvstatus);
                    final TextView tvNotice = (TextView) findViewById(R.id.tvalrtnotice) ;


                    tvdate.setText(alrtdate);
                    tvstatuse.setText(alrtstatus);
                    tvNotice.setText(alrtnotice);



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






}
