package com.shopnosoft.earthquakeresponsesystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmergencyActivity extends AppCompatActivity {

    Button police_control_room, rab_head_quarter, industrial_police, tourist_police, fire_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        police_control_room = (Button) findViewById(R.id.btn_plc_cntrlrm);
        rab_head_quarter = (Button) findViewById(R.id.btn_rab_hdqrtr);
        industrial_police = (Button) findViewById(R.id.btn_indstrl_plc);
        tourist_police = (Button) findViewById(R.id.btn_tourist_police);
        fire_service = (Button) findViewById(R.id.btn_fireservice);


//Call Police HQ
        police_control_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+8801713398311"));
                startActivity(callIntent);

            }
        });

//Call Rab HQ
        rab_head_quarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+88017777202029"));
                startActivity(callIntent);

            }
        });

//Call Industrial Police
        industrial_police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+8801730739675"));
                startActivity(callIntent);

            }
        });

//Call Rab HQ
        tourist_police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+8801769690740"));
                startActivity(callIntent);

            }
        });

//Call Rab HQ
        fire_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:029555555"));
                startActivity(callIntent);

            }
        });
    }
}
