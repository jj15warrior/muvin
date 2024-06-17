package com.jj15.muffin;

import android.location.LocationListener;
import android.os.Bundle;
import android.widget.RelativeLayout;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/*
 * ordinary interface for location listener
 * calls drawer.mapFixedPoint() and relativeLayout.invalidate() when location changes
 * @author jj15
 */

public class Locator implements LocationListener {
    public GeoPoint myLocation = new GeoPoint(0.0, 0.0);
    private Drawer drawer;
    private RelativeLayout relativeLayout;
    public Locator(MapView map, Drawer drawer, RelativeLayout relativeLayout) {
        this.drawer = drawer;
        this.relativeLayout = relativeLayout;
    }



    public GeoPoint getLocation() {
        return myLocation;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        Thread newThread = new Thread(() -> {
            myLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
            drawer.mapFixedPoint(myLocation);
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

