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

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("posts");

        switch (this.mapZustand) {

            case "regionales":
                mMap = googleMap;
                mMap.clear();

                mDatabase.addValueEventListener(new ValueEventListener() {

                    public void onChildAdded(DataSnapshot dataSnapshot) {

                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Post post = dataSnapshot.child(noteDataSnapshot.getKey()).getValue(Post.class);

                            LatLng test = new LatLng(post.getlatitude(), post.getlongitude()); // Anderer Marker
                            mMap.addMarker(new MarkerOptions().position(test).title(post.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hear_me_blue)).snippet(noteDataSnapshot.getKey()));

                        }
                    }

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Post post = dataSnapshot.child(noteDataSnapshot.getKey()).getValue(Post.class);

                            if(post.cat.equals("Regionales")) {
                                LatLng test = new LatLng(post.getlatitude(), post.getlongitude()); // Anderer Marker
                                mMap.addMarker(new MarkerOptions().position(test).title(post.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hear_me_blue)).snippet(noteDataSnapshot.getKey()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(test));
                            }

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

                mDatabase.addValueEventListener(new ValueEventListener() {

                    public void onChildAdded(DataSnapshot dataSnapshot) {

                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Post post = dataSnapshot.child(noteDataSnapshot.getKey()).getValue(Post.class);

                            LatLng test = new LatLng(post.getlatitude(), post.getlongitude()); // Anderer Marker
                            mMap.addMarker(new MarkerOptions().position(test).title(post.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hear_me_orange)).snippet(noteDataSnapshot.getKey()));

                        }
                    }

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Post post = dataSnapshot.child(noteDataSnapshot.getKey()).getValue(Post.class);

                            if(post.cat.equals("Party")) {
                                LatLng test = new LatLng(post.getlatitude(), post.getlongitude()); // Anderer Marker
                                mMap.addMarker(new MarkerOptions().position(test).title(post.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hear_me_orange)).snippet(noteDataSnapshot.getKey()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(test));
                            }

                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        // Log.w(TAG, "Failed to read value.", error.toException());
                    }

                });

                break;

            case "essen":
                mMap = googleMap;
                mMap.clear();

                mDatabase.addValueEventListener(new ValueEventListener() {

                    public void onChildAdded(DataSnapshot dataSnapshot) {

                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Post post = dataSnapshot.child(noteDataSnapshot.getKey()).getValue(Post.class);

                            if(post.cat.equals("Essen")) {
                                LatLng test = new LatLng(post.getlatitude(), post.getlongitude()); // Anderer Marker
                                mMap.addMarker(new MarkerOptions().position(test).title(post.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hear_me_gruen)).snippet(noteDataSnapshot.getKey()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(test));
                            }

                        }
                    }

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Post post = dataSnapshot.child(noteDataSnapshot.getKey()).getValue(Post.class);


                            if(post.cat.equals("Essen")) {
                                LatLng test = new LatLng(post.getlatitude(), post.getlongitude()); // Anderer Marker
                                mMap.addMarker(new MarkerOptions().position(test).title(post.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hear_me_gruen)).snippet(noteDataSnapshot.getKey()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(test));
                            }


                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        // Log.w(TAG, "Failed to read value.", error.toException());
                    }

                });


                break;



            case "shopping":
                mMap = googleMap;
                mMap.clear();

                mDatabase.addValueEventListener(new ValueEventListener() {

                    public void onChildAdded(DataSnapshot dataSnapshot) {

                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Post post = dataSnapshot.child(noteDataSnapshot.getKey()).getValue(Post.class);

                            LatLng test = new LatLng(post.getlatitude(), post.getlongitude()); // Anderer Marker
                            mMap.addMarker(new MarkerOptions().position(test).title(post.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hear_me_rot)).snippet(noteDataSnapshot.getKey()));

                        }
                    }

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            Post post = dataSnapshot.child(noteDataSnapshot.getKey()).getValue(Post.class);

                            if(post.cat.equals("Shopping")) {
                                LatLng test = new LatLng(post.getlatitude(), post.getlongitude()); // Anderer Marker
                                mMap.addMarker(new MarkerOptions().position(test).title(post.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hear_me_rot)).snippet(noteDataSnapshot.getKey()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(test));
                            }
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        // Log.w(TAG, "Failed to read value.", error.toException());
                    }

                });

                break;
        }

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



