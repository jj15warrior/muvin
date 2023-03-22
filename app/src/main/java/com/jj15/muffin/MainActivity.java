package com.jj15.muffin;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;

public class MainActivity extends Activity {
    MapView map = null;
    private RelativeLayout relativeLayout;
    CacheNetController cacheNetController = new CacheNetController();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load/initialize the osmdroid configuration
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));


        setContentView(R.layout.root);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //location perm check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1); // ask for permission
            return;
        }



        map = (MapView) findViewById(R.id.map);
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        map.getController().setZoom(13.0);
        map.getController().setCenter(new GeoPoint(52.2068,21.0495)); // center on Warsaw, if we can't get the user's location
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);



        /*
            TODO: write a server and not push it's address to github
         */

        //initialize customview
        relativeLayout = findViewById(R.id.idRLView);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.purple_200));

        String uuid = cacheNetController.prefetchUuid(true);

        PinMinimal tmp = new PinMinimal(52.2068, 21.0495, "test", "no description", null, paint, uuid);
        cacheNetController.addPin(tmp, true);

        // calling our  paint view class and adding
        // its view to our relative layout.
        Drawer drawer = new Drawer(this);
        relativeLayout.addView(drawer);

        //creating a modified location listener
        LocationListener location = new Locator(map, drawer, relativeLayout, cacheNetController);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, location); // requesting location updates

        cacheNetController.getMe().login("test", "test", true);


        //when the "center" button is clicked, we center on the user's location (from Locator class)
        Button centerOnLocation = (Button) findViewById(R.id.findme);
        // TODO: add graphic "target" button with no background

        centerOnLocation.setOnClickListener(v -> {
            GeoPoint myLocation = ((Locator) location).getLocation();
            if(myLocation.getLatitude() != 0.0 && myLocation.getLongitude() != 0.0) {
                map.getController().animateTo(myLocation);
                map.getController().setCenter(myLocation);
            }
        });
    }

    public void onResume(){
        super.onResume();
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

}
