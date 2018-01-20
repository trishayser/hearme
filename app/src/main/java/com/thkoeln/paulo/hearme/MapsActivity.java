package com.thkoeln.paulo.hearme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;

    public static final String EXTRA_ID = "com.thkoeln.paulo.hearme.MESSAGE";


    public void setMapZustand(String mapZustand) {
        this.mapZustand = mapZustand;
    }

    private String mapZustand;
    SupportMapFragment mapFragment;
    FloatingActionButton fab;
    double longitude;
    double latitude;


    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Zustand Map auf Start setzten
        mapZustand = "regionales";

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
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
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

        final Button bRegionales = (Button) findViewById(R.id.regionales);
        bRegionales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapZustand("regionales");
                mapFragment.getMapAsync(MapsActivity.this);

                int farbe = getResources().getColor(R.color.regionales);
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

        final Button bEssen = (Button) findViewById(R.id.essen);
        bEssen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapZustand("essen");
                mapFragment.getMapAsync(MapsActivity.this);

                int farbe = getResources().getColor(R.color.essen);
                fab.getBackground().setTint(farbe);

            }

        });


        final Button bShopping = (Button) findViewById(R.id.shopping);
        bShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMapZustand("shopping");
                mapFragment.getMapAsync(MapsActivity.this);
                int farbe = getResources().getColor(R.color.shopping);
                fab.getBackground().setTint(farbe);
            }

        });
    }


    // Karte
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        switch (this.mapZustand) {

            case "regionales":
                mMap = googleMap;
                mMap.clear();
/*
                LatLng koelnHbf2 = new LatLng(50.942545, 6.956976); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(koelnHbf2).title("Museum besuchen?").icon(BitmapDescriptorFactory.fromResource(R.drawable.markertest)));

                LatLng koelnHbf3 = new LatLng(50.942453, 6.956466); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(koelnHbf3).title("Dom besichtigen?").icon(BitmapDescriptorFactory.fromResource(R.drawable.markertest)));

                LatLng koelnHbf4 = new LatLng(50.942206, 6.956721); // Anderer Marker
                mMap.addMarker(new MarkerOptions().position(koelnHbf4).title("Jazz hören?").icon(BitmapDescriptorFactory.fromResource(R.drawable.markertest)));
*/
                //Unfixed Database

                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference("posts");


                mDatabase.addValueEventListener(new ValueEventListener() {

                    public void onChildAdded(DataSnapshot dataSnapshot) {

                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Post post = dataSnapshot.child(noteDataSnapshot.getKey()).getValue(Post.class);

                            LatLng test = new LatLng(post.getlatitude(), post.getlongitude()); // Anderer Marker
                            mMap.addMarker(new MarkerOptions().position(test).title(post.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).snippet(noteDataSnapshot.getKey()));

                        }
                    }

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Post post = dataSnapshot.child(noteDataSnapshot.getKey()).getValue(Post.class);

                            LatLng test = new LatLng(post.getlatitude(), post.getlongitude()); // Anderer Marker
                            mMap.addMarker(new MarkerOptions().position(test).title(post.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).snippet(noteDataSnapshot.getKey()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(test));

                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                       // Log.w(TAG, "Failed to read value.", error.toException());
                    }

                });



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

            case "essen":
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

            case "shopping":
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
                Intent intent = new Intent(MapsActivity.this, PlayActivity.class);
                intent.putExtra(EXTRA_ID, marker.getSnippet());
                marker.showInfoWindow();
                //startActivity(intent);

                return true;
            }
        });




        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapsActivity.this, PlayActivity.class);
                intent.putExtra(EXTRA_ID, marker.getSnippet());
                startActivity(intent);
            }
        });

        //testing if git works

        // End - Testing Map Marker starting Activity


        // Karte aktualisieren / erstelle

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(longitude, latitude)));

        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
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

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }
}



