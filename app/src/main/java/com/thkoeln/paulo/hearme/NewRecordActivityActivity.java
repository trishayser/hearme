package com.thkoeln.paulo.hearme;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.concurrent.atomic.AtomicMarkableReference;


public class NewRecordActivityActivity extends AppCompatActivity {

    ImageButton recordPlay;
    AudioRecordTest audioRecordTest;
    CountDownTimer testTimer;

    int test3, progress, currentLength = 0;

    boolean mStartRecording = true; // Für Record
    boolean mStartPlaying = true; // Für Play

    long millisUntilFinished;

    /// Recordtest
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    // Requesting permission to RECORD_AUDIO
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    // Ende - Requesting permission to RECORD_AUDIO

    // Requesting permission to ACCESS ... Fine
    private static final int REQUEST_LOCATION_FINE_PERMISSION = 100;
    private boolean permissionToLocationFAccepted = false;
    private String[] permissionsLf = {Manifest.permission.ACCESS_FINE_LOCATION};
    // Ende - Requesting permission

    // Requesting permission to ACCESS... Coarse
    private static final int REQUEST_LOCATION_COARSE_PERMISSION = 101;
    private boolean permissionToLocationCAccepted = false;
    private String[] permissionsLc = {Manifest.permission.ACCESS_COARSE_LOCATION};
    // Ende - Requesting permission


    private Button abschicken;
    private ImageButton recordButton;
    private BroadcastReceiver broadcastReceiver;
    private EditText kommentar;
    private EditText titel_edit;
    private EditText kat_edit;


    @Override
    protected void onResume() {
        super.onResume();
        permissionLocation();
        permissionAudio();
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (broadcastReceiver == null) {
//            broadcastReceiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    kommentar.setText(intent.getExtras().get("coordinates").toString());
//                    System.out.println("Koordinaten" + "\n" + intent.getExtras().get("coordinates"));
//                    System.out.println("Test");
//                }
//            };
//        }
//        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        //Intent i = new Intent(getApplicationContext(), GPS_Service.class);
        //stopService(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_FINE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    permissionToLocationFAccepted = true;

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    //Toast.makeText(this, "Fine??", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case REQUEST_LOCATION_COARSE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    //Toast.makeText(this, "COARSE??", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case REQUEST_RECORD_AUDIO_PERMISSION: {
//                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableRecordButton();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    //Toast.makeText(this, "Audio??", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_record_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //--------------------AudioRecordTest-Klasse----------------------------------------
        mFileName = getExternalCacheDir().getAbsolutePath(); //Original
        mFileName += "/audiorecordtest.3gp";
        audioRecordTest = new AudioRecordTest(mFileName);
        //------------------------------------------------------------

        final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        final LinearLayout record_player = (LinearLayout) findViewById(R.id.record_player);
        recordButton = (ImageButton) findViewById(R.id.record);

        final Intent test = new Intent(this, AudioRecordTest.class);

        // perform click event on button
        recordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    simpleProgressBar.setVisibility(View.VISIBLE);

                    audioRecordTest.onRecord(mStartRecording);
                    mStartRecording = false;


                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    simpleProgressBar.setVisibility(View.INVISIBLE);
                    record_player.setVisibility(View.VISIBLE);
                    audioRecordTest.onRecord(mStartRecording);
                    mStartRecording = true;


                    return true;
                }
                return false;
            }
        });


        abschicken = (Button) findViewById(R.id.abschicken);
        final Intent abschickenIntent = new Intent(this, MapsActivity.class);


        abschicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Write a message to the database

                titel_edit = (EditText) findViewById(R.id.titel_edit);
                String title = titel_edit.getText().toString();

                kat_edit = (EditText) findViewById(R.id.kat_edit);
                String kat = titel_edit.getText().toString();

                if (validate(title)) {
                    System.out.println("Titel nicht leer");

                    kommentar = (EditText) findViewById(R.id.comment);

                    if (location() == null) {
                        System.out.println("loc ist null");
                        Toast.makeText(NewRecordActivityActivity.this, "Loc ist null", Toast.LENGTH_SHORT).show();

                    } else {

                        double longitude = location().getLongitude();
                        double latitude = location().getLatitude();

                        //kommentar.setText(latitude + " " + longitude);

                    }


                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    String PostId = mDatabase.push().getKey();
                    Post post = new Post(2, titel_edit.getText().toString(), "admin", location().getLatitude(), location().getLongitude(), kat_edit.getText().toString());

                    mDatabase.child("posts").child(PostId).setValue(post);

                    //Storage save data
                    StorageReference storageRef;
                    storageRef = FirebaseStorage.getInstance().getReference();

                    Uri file = Uri.fromFile(new File(mFileName));
                    StorageReference riversRef = storageRef.child(PostId + ".3gp");

                    riversRef.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    // ...
                                }
                            });

                    startActivity(abschickenIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Anmeldung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                }


            }
        });


        final ProgressBar recordProgressbar = (ProgressBar) findViewById(R.id.progressBar_2_record);
        recordProgressbar.setProgress(0);

        recordPlay = (ImageButton) findViewById(R.id.play_record);


        testTimer = new CountDownTimer(30000, 100) {


            public void onTick(long millisUntilFinished) {

                currentLength = audioRecordTest.getmPlayer().getCurrentPosition();
                int maxLength = audioRecordTest.getmPlayer().getDuration();
                progress = ((currentLength * 100) / maxLength);

                System.out.println("Länge in Timer " + maxLength);

                recordProgressbar.setProgress(progress);
                System.out.println(currentLength);
                System.out.println(progress);
                System.out.println("tick");

                test3 = test3 + 100;

                if (test3 >= maxLength) {
                    currentLength = 0;
                    test3 = 0;
                    onFinish()
                    ;


                }

            }

            public void onFinish() {
                recordProgressbar.setProgress(0);
                recordPlay.setImageResource(R.drawable.play);
                audioRecordTest.onPlay(mStartPlaying);
                mStartPlaying = true;
                this.cancel();
                System.out.println("Nachricht fertig abgespiel!");
            }
        };

        recordPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                audioRecordTest.onPlay(mStartPlaying);
                if (mStartPlaying) {
                    recordPlay.setImageResource(R.drawable.pause);
                    testTimer.start();

                } else {
                    recordPlay.setImageResource(R.drawable.play);
                    recordProgressbar.setProgress(0);
                    testTimer.cancel();
                    progress = 0;
                    currentLength = 0;
                }
                mStartPlaying = !mStartPlaying;

            }

        });

    }

    public Location location() {

        permissionLocation();

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        criteria.setSpeedRequired(false);

        String locationProvider = lm.NETWORK_PROVIDER;
        // Or use LocationManager.GPS_PROVIDER

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location loc = lm.getLastKnownLocation(locationProvider);

//        String bestLocationProvider = lm.getBestProvider (criteria,true );
//        System.out.println(bestLocationProvider);
//       // LocationProvider lp = lm.getProvider (bestLocationProvider);
//        Location loc = lm.getLastKnownLocation (lm.NETWORK_PROVIDER);

        return loc;
    }

    public void enableRecordButton() {
        System.out.println("hallo");
    }

    // Permissions
    public void permissionAudio() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_RECORD_AUDIO_PERMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        // Ende - Permissions

    }

    public void permissionLocation() {
        // Permissions

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_FINE_PERMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        // Ende - Permissions

    }

    public boolean validate(String title) {
        boolean validate = true;

        if ((title.isEmpty())) {
            titel_edit.setError("Mindestens 3 Zeichen");
            validate = false;
        }

            return validate;

    }

}