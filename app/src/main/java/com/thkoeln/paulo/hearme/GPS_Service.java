package com.thkoeln.paulo.hearme;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by pascal on 18.12.17.
 */

public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    private LocationManager lm;
    private Criteria criteria;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    public void onCreate(){
        Toast.makeText(this, "service creating", Toast.LENGTH_SHORT).show();

    }

//        locationManager = (LocationManager) getApplicationContext().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        final Intent i = new Intent("location_update");
//        listener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
////                Intent i = new Intent("location_update");
//                i.putExtra("coordinates", location.getLongitude() + " " + location.getLatitude());
//                System.out.println("Longtitude: " + location.getLongitude() +" Latitude: "+ location.getLatitude());
//                sendBroadcast(i);
//
//
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
//            }
//        };
//        locationManager = (LocationManager) getApplicationContext().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3,0, listener);
//    }





    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        return START_STICKY;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        this.stopSelf();
        Toast.makeText(this, "service destroyed", Toast.LENGTH_SHORT).show();

//        if(locationManager!=null){
//            locationManager.removeUpdates(listener);
//        }
    }
 }
