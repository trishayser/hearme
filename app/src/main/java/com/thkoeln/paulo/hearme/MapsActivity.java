package com.thkoeln.paulo.hearme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    public void setMapZustand(String mapZustand) {
        this.mapZustand = mapZustand;
    }

    private String mapZustand;
    SupportMapFragment mapFragment;
    FloatingActionButton fab;
    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Zustand Map auf Start setzten
        mapZustand = "kultur";


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);
//        criteria.setAltitudeRequired(true);
//        criteria.setSpeedRequired(false);
//        String bestLocationProvider = lm.getBestProvider(criteria, true);
//        LocationProvider lp = lm.getProvider(bestLocationProvider);
//        Location loc = lm.getLastKnownLocation(bestLocationProvider);
//        double longitude = loc.getLongitude();
//        double latitude = loc.getLatitude();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //FAB RECORD CLICK LISTENER
        fab = (FloatingActionButton) findViewById(R.id.fab);
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

//        final Intent playbackServiceIntent = new Intent(this, AudioPlayer.class);
//        FloatingActionButton play = (FloatingActionButton) findViewById(R.id.floatingActionButton);
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                startService(playbackServiceIntent);
//                finish();
//            }
//
//        });


        //Button Map Listener Click LISTENER ( Kultur, Party, Sport, Flirt)


        final Button bKultur = (Button) findViewById(R.id.kultur);
        bKultur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapZustand("kultur");
                mapFragment.getMapAsync(MapsActivity.this);

                int farbe = getResources().getColor(R.color.kultur);
                fab.getBackground().setTint(farbe);
            }

        });

        final Button bParty = (Button) findViewById(R.id.party);

        bParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapZustand("party");
                mapFragment.getMapAsync(MapsActivity.this);

                int farbe = getResources().getColor(R.color.party);
                fab.getBackground().setTint(farbe);
            }

        });

        final Button bSport = (Button) findViewById(R.id.sport);
        bSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapZustand("sport");
                mapFragment.getMapAsync(MapsActivity.this);

                int farbe = getResources().getColor(R.color.sport);
                fab.getBackground().setTint(farbe);

            }

        });


        final Button bFlirt = (Button) findViewById(R.id.flirt);
        bFlirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapZustand("flirt");
                mapFragment.getMapAsync(MapsActivity.this);
                int farbe = getResources().getColor(R.color.ratingbar);
                fab.getBackground().setTint(farbe);
            }

        });
    }



















    // Karte
    @Override
    public void onMapReady(GoogleMap googleMap) {

        switch (this.mapZustand) {

            case "kultur":
                mMap = googleMap;
                mMap.clear();
                // Add a marker in Sydney and move the camera
                // LatLng sydney = new LatLng(0, 0);


                LatLng koelnHbf2 = new LatLng(50.942545, 6.956976); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(koelnHbf2).title("Museum besuchen?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                LatLng koelnHbf3 = new LatLng(50.942453, 6.956466); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(koelnHbf3).title("Dom besichtigen?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                LatLng koelnHbf4 = new LatLng(50.942206, 6.956721); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(koelnHbf4).title("Jazz hören?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                //Unfixed Database
                /*
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List posts = new ArrayList<>();


                        Log.d(TAG, "Title: " + post.getTitle() + ", GPS " + post.getGPS());

                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Post post = dataSnapshot.child("1").getValue(Post.class);
                            Log.d(TAG, "Title: " + post.getTitle() + ", GPS " + post.getGPS());
                            posts.add(post);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
                */
                break;

            case "party":
                mMap = googleMap;
                mMap.clear();
                // Add a marker in Sydney and move the camera
                // LatLng sydney = new LatLng(0, 0);

                LatLng pKoelnHbf2 = new LatLng(50.941658, 6.955746); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(pKoelnHbf2).title("Disco?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                LatLng pKoelnHbf3 = new LatLng(50.940613, 6.957943); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(pKoelnHbf3).title("Komasaufen").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                LatLng pKoelnHbf4 = new LatLng(50.940826, 6.962819); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(pKoelnHbf4).title("Silvester").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                break;

            case "sport":
                mMap = googleMap;
                mMap.clear();
                // Add a marker in Sydney and move the camera
                // LatLng sydney = new LatLng(0, 0);

                LatLng sKoelnHbf2 = new LatLng(50.941941, 6.961923); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(sKoelnHbf2).title("Fußball?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                LatLng sKoelnHbf3 = new LatLng(50.945284, 6.954198); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(sKoelnHbf3).title("Radfahren?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                LatLng sKoelnHbf4 = new LatLng(50.944213, 6.949037); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(sKoelnHbf4).title("Joggen?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                break;

            case "flirt":
                mMap = googleMap;
                mMap.clear();
                // Add a marker in Sydney and move the camera
                // LatLng sydney = new LatLng(0, 0);
                LatLng fKoelnHbf2 = new LatLng(50.946106, 6.938839); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(fKoelnHbf2).title("Suche neuen Freund").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                LatLng fKoelnHbf3 = new LatLng(50.943149, 6.942664); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(fKoelnHbf3).title("Suche neue Freundin").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

                LatLng fKoelnHbf4 = new LatLng(50.940163, 6.954418); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(fKoelnHbf4).title("Hallo, i bims, a Singel").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

                break;
        }

        LatLng ichKoelnHbf = new LatLng(50.942214, 6.957593); //Meine Position
        mMap.addMarker(new MarkerOptions().position(ichKoelnHbf).title("ICH"));



        // Testing Map Marker starting Activity

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(MapsActivity.this ,PlayActivity.class);
                startActivity(intent);

                return true;
            }
        });

        //testing if git works

        // End - Testing Map Marker starting Activity


        // Karte aktualisieren / erstellen
        //mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ichKoelnHbf));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(longitude, latitude)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
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

            case R.id.abmelden:
                System.out.println("abmelden");
                // Intent erzeugen und Starten der AktiendetailActivity mit explizitem Intent
                Intent logInIntent = new Intent(this, LoginStart.class);
                // settingsIntent.putExtra(Intent.EXTRA_TEXT, aktienInfo);
                startActivity(logInIntent);
                break;
        }
        return super.onOptionsItemSelected(item); //To change body of generated methods, choose Tools | Templates.
    }
    // Edit Pascal Ende
    // Nichts


}



