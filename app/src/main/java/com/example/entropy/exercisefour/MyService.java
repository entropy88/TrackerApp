package com.example.entropy.exercisefour;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by entropy on 4/1/2018.
 */

public class MyService extends Service {
    LocationListener locationListener;
    LocationManager locationManager;
    String longitude;
    String lattitude;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            longitude=String.valueOf(location.getLongitude());
                lattitude=String.valueOf(location.getLatitude());
                Toast.makeText(MyService.this,longitude+lattitude,Toast.LENGTH_LONG).show();
                Toast.makeText(MyService.this,"service started",Toast.LENGTH_LONG).show();
                Intent intentToSend= new Intent();
                intentToSend.setAction("intent_sent");
                intentToSend.putExtra("data_to_send",longitude+lattitude);
                sendBroadcast(intentToSend);
                Toast.makeText(MyService.this,"Intent sent",Toast.LENGTH_LONG).show();



            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

 try {
        locationManager.requestLocationUpdates("gps", 10000, 0, locationListener);
 }
 catch (SecurityException e){
     Toast.makeText(MyService.this,"Please enable GPS",Toast.LENGTH_LONG).show();
 }


//        Toast.makeText(this,"service started",Toast.LENGTH_LONG).show();
//        Intent intentToSend= new Intent();
//        intentToSend.setAction("intent_sent");
//        intentToSend.putExtra("data_to_send",longitude+lattitude);
//        sendBroadcast(intentToSend);
//        Toast.makeText(this,"Intent sent",Toast.LENGTH_LONG).show();




        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"service stopped",Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
