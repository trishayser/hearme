package com.thkoeln.paulo.hearme;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by pascal on 18.12.17.
 */

public class GPS_Service extends Service {


//    private LocationListener listener;
//    private LocationManager locationManager;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @SuppressLint("MissingPermission")
//    @Override
//    public void onCreate(){
//
//        locationManager = (LocationManager) getApplicationContext().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        locationManager.
//       final Intent i = new Intent("location_update");
//        listener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
////                Intent i = new Intent("location_update");
//                i.putExtra("coordinates", location.getLongitude() + " " + location.getLatitude());
//                System.out.println("Longtitude: " + location.getLongitude() +" Latitude: "+ location.getLatitude());
//                sendBroadcast(i);
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
//            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//            }
//        };
//        locationManager = (LocationManager) getApplicationContext().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, listener);
//    }
//
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        if(locationManager!=null){
//            locationManager.removeUpdates(listener);
//        }
//    }


}