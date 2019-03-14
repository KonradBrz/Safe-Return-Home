package com.tashfaelapp.konrad.safereturnhome;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

public class LocationNETWORK implements ActivityCompat.OnRequestPermissionsResultCallback{

    private Context mContext;
    private String email;
    private int time;

    static LocationManager mLocationManager;
    static LocationListener mLocationListenerNETWORK;

    private static Location locNETWORK;
    private static int altitude;
    private static int accuracy;
    private static float speed;

    private final static int t = 0;
    static boolean locationNETWORK = false;

    private static double longitude;
    private static double latitude;

    LocationNETWORK(final Context mContext, final String email, final int time) {

        locationNETWORK = true;

        this.time = time;
        this.mContext = mContext;
        this.email = email;

        Log.i("INFO", "=========START==========");

        Log.i("INFO", "onLocationChanged: SMS" + Blank_Sms.returnSmsOrMailBool());
        Log.i("INFO", "onLocationChanged: MAIL" + Blank_Mail.returnSmsOrMailBool());

        mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        mLocationListenerNETWORK = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //Log.i("INFO", "LAST LOCATION NETWORK: " + locNETWORK.toString());

                Log.i("ACCURACY", "onLocationChanged: "+ location.getAccuracy());
                Log.i("HAS ACCURACY", "onLocationChanged: "+ location.hasAccuracy());
                Log.i("ALTITUDE", "onLocationChanged: "+ location.getAltitude());

                //locationNETWORK = true;

                altitude = (int) location.getAltitude();
                accuracy = (int) location.getAccuracy();
                speed = (int) location.getSpeed();

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                DataLocationAndWeather dataLocationAndWeather = new DataLocationAndWeather(latitude, longitude, altitude, accuracy, (int) speed);
                dataLocationAndWeather.getCurrentData();

                //because I create new object with new different data
                SendMail.MyTask myMailTask = new SendMail.MyTask((MainActivity) mContext, email, "My location network", (String) location.toString().subSequence(17, 36), (int) location.getAccuracy(), (int) location.getAltitude());
                myMailTask.execute();
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

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions((Activity) mContext, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {

            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, time,0,mLocationListenerNETWORK);

            locNETWORK = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, t, 0, mLocationListenerNETWORK);

                locNETWORK = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
    }

    static Location getLocation () {

        return locNETWORK;
    }

    static int getAltitude () {

        return altitude;
    }

    static int getAccuracy () {

        return accuracy;
    }

    static boolean getLocationNETWORK () {

        return locationNETWORK;
    }

    static double getLongitude() {
        return longitude;
    }

    static double getLatitude() {
        return latitude;
    }
}
