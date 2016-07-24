package com.shopnosoft.earthquakeresponsesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogoutActivity_004 extends AppCompatActivity {


    UserLocalStore userLocalStore;

    EditText etUsernameview,etemialview;
    TextView tvWelcomeMessage;

    Button logoutButton;

    //for Double back press
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_activity_004);



//Fields
        etUsernameview = (EditText) findViewById(R.id.etUsernameview);
        etemialview = (EditText) findViewById(R.id.etemialview);
        tvWelcomeMessage = (TextView) findViewById(R.id.tvWelcomeMessage);
        logoutButton = (Button) findViewById(R.id.btLogout);

        userLocalStore = new UserLocalStore(this);

        displayUserDetailspfl();
        //////////////////////
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                Intent intent = new Intent(LogoutActivity_004.this, LoginActivity_002.class);
                LogoutActivity_004.this.startActivity(intent);
                finish();
            }
        });





    }


/////////////////////////////

    private void displayUserDetailspfl(){
        User user = userLocalStore.getUserLoggedIn();
        etUsernameview.setText(user.username);
        etemialview.setText(user.email);
        String message = user.name + " Welcome to your user area";
        tvWelcomeMessage.setText(message);
    }
    //////////////////////////////


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
