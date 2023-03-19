package com.jj15.muffin;

import android.location.LocationListener;
import android.os.Bundle;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

public class Locator implements LocationListener {
    public GeoPoint myLocation = new GeoPoint(0.0, 0.0);
    private MapView map;

    public Locator(MapView map) {
        this.map = map;
    }

    public GeoPoint getLocation() {
        return myLocation;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        myLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
        System.out.println("Location changed: " + myLocation.toString());
        Projection projection = map.getProjection();
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

