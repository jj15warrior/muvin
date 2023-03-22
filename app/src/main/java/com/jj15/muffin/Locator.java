package com.jj15.muffin;

import android.location.LocationListener;
import android.os.Bundle;
import android.widget.RelativeLayout;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class Locator implements LocationListener {
    public GeoPoint myLocation = new GeoPoint(0.0, 0.0);
    private MapView map;
    private Drawer drawer;
    private RelativeLayout relativeLayout;
    private CacheNetController cacheNetController;
    public Locator(MapView map, Drawer drawer, RelativeLayout relativeLayout, CacheNetController cacheNetController) {
        this.map = map;
        this.drawer = drawer;
        this.relativeLayout = relativeLayout;
        this.cacheNetController = cacheNetController;
    }



    public GeoPoint getLocation() {
        return myLocation;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        Thread newThread = new Thread(() -> {
            myLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
            drawer.mapFixedPoint(myLocation, map, relativeLayout, cacheNetController);
            relativeLayout.invalidate();
        });
        newThread.start();
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

