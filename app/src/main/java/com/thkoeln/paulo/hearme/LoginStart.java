package com.thkoeln.paulo.hearme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginStart extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_start);

        final Button login = (Button) findViewById(R.id.anmelden);
        final Intent start = new Intent(this ,MapsActivity.class);

        final Button registration = (Button) findViewById(R.id.registrieren);
        final Intent registrationIntent = new Intent(this ,Registration.class);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(start);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(registrationIntent);
            }
        });


    }
}
