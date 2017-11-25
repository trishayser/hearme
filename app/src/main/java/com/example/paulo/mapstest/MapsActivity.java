package com.example.paulo.mapstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {



    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //FAB RECORD CLICK LISTENER
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Neue Nachricht");

                // Intent erzeugen und Starten der AktiendetailActivity mit explizitem Intent
                Intent testIntent = new Intent(getApplication().getApplicationContext(), NewRecordActivityActivity.class);
                // settingsIntent.putExtra(Intent.EXTRA_TEXT, aktienInfo);
                startActivity(testIntent);


//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        //FAB PLAY CLICK LISTENER

        final Intent playbackServiceIntent = new Intent(this, AudioPlayer.class);
        FloatingActionButton play = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    startService(playbackServiceIntent);
                    finish();
                }

        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(0, 0);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));
        //mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    // Edit Pascal

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //menu activity bekannt
         MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //click listener quasi.

        switch (item.getItemId()) {
            case R.id.profil_change:
                System.out.println("Profil wurde geändert");

                // Intent erzeugen und Starten der AktiendetailActivity mit explizitem Intent
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
               // settingsIntent.putExtra(Intent.EXTRA_TEXT, aktienInfo);
                startActivity(settingsIntent);

                break;
        }
        return super.onOptionsItemSelected(item); //To change body of generated methods, choose Tools | Templates.
    }
    // Edit Pascal Ende
}
