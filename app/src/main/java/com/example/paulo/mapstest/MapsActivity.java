package com.example.paulo.mapstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public void setMapZustand(String mapZustand) {
        this.mapZustand = mapZustand;
    }

    private String mapZustand;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Zustand Map auf Start setzten
        mapZustand = "kultur";


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
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


        //Button Map Listener Click LISTENER ( Kultur, Party, Sport, Flirt)

        final Button bKultur = (Button) findViewById(R.id.kultur);
        bKultur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setMapZustand("kultur");
                mapFragment.getMapAsync(MapsActivity.this);
            }

        });

        final Button bParty = (Button) findViewById(R.id.party);
        bParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapZustand("party");
                mapFragment.getMapAsync(MapsActivity.this);
            }

        });

        final Button bSport = (Button) findViewById(R.id.sport);
        bSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapZustand("sport");
                mapFragment.getMapAsync(MapsActivity.this);
            }

        });


        final Button bFlirt = (Button) findViewById(R.id.flirt);
        bFlirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapZustand("flirt");
                mapFragment.getMapAsync(MapsActivity.this);
            }

        });
    }



















    // Karte
    @Override
    public void onMapReady(GoogleMap googleMap) {

        switch (this.mapZustand) {

            case "kultur":
                mMap = googleMap;
                // Add a marker in Sydney and move the camera
                // LatLng sydney = new LatLng(0, 0);

                LatLng koelnHbf2 = new LatLng(50.942545, 6.956976); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(koelnHbf2).title("Museum besuchen?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                LatLng koelnHbf3 = new LatLng(50.942453, 6.956466); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(koelnHbf3).title("Dom besichtigen?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                LatLng koelnHbf4 = new LatLng(50.942206, 6.956721); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(koelnHbf4).title("Jazz hören?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                break;

            case "party":
                mMap = googleMap;
                // Add a marker in Sydney and move the camera
                // LatLng sydney = new LatLng(0, 0);

                LatLng pKoelnHbf2 = new LatLng(50.942545, 6.956976); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(pKoelnHbf2).title("Disco?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                LatLng pKoelnHbf3 = new LatLng(50.942453, 6.956466); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(pKoelnHbf2).title("Komasaufen").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                LatLng pKoelnHbf4 = new LatLng(50.942206, 6.956721); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(pKoelnHbf2).title("Silvester").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                break;
                
            case "sport":
                mMap = googleMap;
                // Add a marker in Sydney and move the camera
                // LatLng sydney = new LatLng(0, 0);

                LatLng kKoelnHbf2 = new LatLng(50.942545, 6.956976); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(kKoelnHbf2).title("Fußball?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                LatLng kKoelnHbf3 = new LatLng(50.942453, 6.956466); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(kKoelnHbf3).title("Radfahren?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                LatLng kKoelnHbf4 = new LatLng(50.942206, 6.956721); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(kKoelnHbf4).title("Joggen?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                break;

            case "flirt":
                mMap = googleMap;
                // Add a marker in Sydney and move the camera
                // LatLng sydney = new LatLng(0, 0);

                LatLng fKoelnHbf2 = new LatLng(50.942545, 6.956976); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(fKoelnHbf2).title("Suche neuen Freund").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                LatLng fKoelnHbf3 = new LatLng(50.942453, 6.956466); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(fKoelnHbf2).title("Suche neue Freundin").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

                LatLng fKoelnHbf4 = new LatLng(50.942206, 6.956721); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(fKoelnHbf2).title("Hallo, i bims, a Singel").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

                break;
        }

        LatLng ichKoelnHbf = new LatLng(50.942214, 6.957593); //Meine Position
        mMap.addMarker(new MarkerOptions().position(ichKoelnHbf).title("ICH"));
        // Karte aktualisieren / erstellen
        //mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ichKoelnHbf));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }
    // Ende Karte




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
