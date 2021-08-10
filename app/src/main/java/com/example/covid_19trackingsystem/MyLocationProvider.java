package com.example.covid_19trackingsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.util.List;

@SuppressLint("MissingPermission")
public class MyLocationProvider {

    Location location;
    LocationManager locationManager;
    private final static long  TIME_BETWEEN_UPDATE = 5*100;
    private final static float DISTANCE_BETWEEN_UPDATE = 400;


    public MyLocationProvider(Context context) {

        this.locationManager =  (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.location = null;
    }

    public boolean ProviderEnabled(){

        boolean Gps = locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER);
        boolean Network = locationManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER);

        return Gps || Network;
    }



    public Location getCurrentLocation(LocationListener locationListener){
        if (!ProviderEnabled())
            return null;
        String Provider = LocationManager.GPS_PROVIDER;
        if (!locationManager.isProviderEnabled(Provider))
            Provider = LocationManager.NETWORK_PROVIDER;
        location = locationManager.getLastKnownLocation(Provider);
        if (location == null)
            location = getBestLocation();
//update location
        if (locationListener != null)
            locationManager.requestLocationUpdates(Provider,TIME_BETWEEN_UPDATE,DISTANCE_BETWEEN_UPDATE,locationListener);

        return location;


    }

    private Location getBestLocation() {
        List<String> Providers = locationManager.getAllProviders();
        Location BestLocation = null ;
        for (String Provider : Providers){

            Location temp = locationManager.getLastKnownLocation(Provider);
            if (temp==null)
                continue;
            if (BestLocation==null)
                BestLocation = temp;
            else {
                if (temp.getAccuracy()>BestLocation.getAccuracy())
                    BestLocation = temp;
            }


        }
        return BestLocation;
    }



}
