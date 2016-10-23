package com.shopnosoft.earthquakeresponsesystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Location_View extends AppCompatActivity {


    //for Double back press
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    String getlcn_usermobile = null;

    TextView lcvw_Name,lcvw_lattitude,lcvw_longitude,lcvw_address;
    Button lcn_btnupdt,lcn_btnvw;

    private static String getLattitude = null, getLongititude = null,getaddress_frm_coordnt=null,Lattitude = null,Longitude = null,Address=null;

    private WifiManager wfm;
    private ConnectivityManager cntm;
    // GPSTracker class
    GPSTracker gps;
    //For Address
    private RequestQueue requestQueue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location__view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        requestQueue = Volley.newRequestQueue(this);


        lcvw_Name = (TextView)findViewById(R.id.tv_lvw_welcmmsg);
        lcvw_lattitude = (TextView)findViewById(R.id.tv_lvw_lattitude);
        lcvw_longitude = (TextView)findViewById(R.id.tv_lvw_longititude);
        lcvw_address = (TextView)findViewById(R.id.tv_lvw_address);
        lcn_btnupdt = (Button)findViewById(R.id.btn_lv_updatelctn);
        lcn_btnvw = (Button)findViewById(R.id.btn_lv_vwlctn);



        SharedPreferences sharedpref_usr = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        final String lc_name=sharedpref_usr.getString("Name_Key","");
        lcvw_Name.setText("Dear \n"+lc_name+"");


        get_location();


        storelocation();

        lcn_btnupdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                get_location();


                storelocation();

            }
        });





        lcn_btnvw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_location();


                storelocation();


                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:"+Lattitude+","+Longitude+""));
                startActivity(intent);

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();


        storelocation();

        get_location();
    }

    @Override
    protected void onStart() {
        super.onStart();

        storelocation();

        get_location();
    }


    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {

            Intent intent = new Intent(Location_View.this, Navigation_Activity_000.class);
            Location_View.this.startActivity(intent);
            finish();
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

    }



    public void get_location(){

        SharedPreferences sharedpref_usr = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        getlcn_usermobile = sharedpref_usr.getString("MobileNo_Key","");


        Response.Listener<String> responseListner = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");

                    if(success){


                        String getlctn_mobileno = jsonresponse.getString("mobileno");
                        String getlctn_lattitude = jsonresponse.getString("lattitude");
                        String getlctn_longititude = jsonresponse.getString("longititude");
                        String getlctn_address = jsonresponse.getString("address");
                        String getlctn_date = jsonresponse.getString("date");
                        String getlctn_time = jsonresponse.getString("time");


                        lcvw_lattitude.setText(getlctn_lattitude);
                        lcvw_longitude.setText(getlctn_longititude);
                        lcvw_address.setText(getlctn_address);

                        }
                    else{
                        Toast.makeText(getBaseContext(), "Unable to get Location !",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };



        GetLocationRequest getLocationRequest = new GetLocationRequest(getlcn_usermobile,responseListner);
        RequestQueue queue = Volley.newRequestQueue(Location_View.this);
        queue.add(getLocationRequest);


    }



    public void storelocation(){
        /////////////////////////////////<<-------------------------------- SHOWING LOCATION CO-ORDINATES  --------------------------->>////////////////////////

        gps = new GPSTracker(Location_View.this);

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
                    if (success) {
                        Toast.makeText(getBaseContext(), "Location Update Successfully!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Location Update Failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        LocationStore_Request locationstrRequest = new LocationStore_Request (strmobileno,Lattitude,Longitude,Address,responseListner );
        RequestQueue queue = Volley.newRequestQueue(Location_View.this);
        queue.add(locationstrRequest);

    }





}