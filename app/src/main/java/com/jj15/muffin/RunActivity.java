package com.jj15.muffin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.jj15.muffin.ble.BleAdapter;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.modules.SqlTileWriter;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class RunActivity extends AppCompatActivity {
    MapView map = null;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    protected final BetterActivityResult<Intent, ActivityResult> activityLauncher = BetterActivityResult.registerActivityForResult(this);
    String logmem = "";
    public void log(String s){
        TextView tv = (TextView) findViewById(R.id.logText);
        logmem += s;
        logmem += "\n";
        if(logmem.split("\n").length > (tv.getHeight()/tv.getLineHeight())){
            logmem = logmem.substring(logmem.indexOf("\n"));
        }
        tv.setText(logmem);
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        super.onCreate(savedInstanceState);

        //load/initialize the osmdroid configuration

        Context context = this;

        setContentView(R.layout.root);

        BleAdapter bleAdapter = new BleAdapter(context);

        bleAdapter.init(activityLauncher);

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //location perm check
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1); // ask for permission
            return;
        }

        File osmdroidBasePath = new File(getApplicationInfo().dataDir);
        Configuration.getInstance().setOsmdroidBasePath(osmdroidBasePath);
        Configuration.getInstance().setUserAgentValue("jj15's movin");
        Configuration.setConfigurationProvider(Configuration.getInstance());

        map = findViewById(R.id.map);

        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        map.getController().setZoom(13.0);
        map.getController().setCenter(new GeoPoint(52.2068, 21.0495)); // center on Warsaw, if we can't get the user's location
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        map.setMultiTouchControls(true);


        displayMetrics = getResources().getDisplayMetrics();


        /*
            TODO: write a server and not push it's address to github
         */

        //initialize customview
        RelativeLayout relativeLayout = findViewById(R.id.idRL);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.purple_200));
        Bitmap bmp;
        try {
            InputStream assetInStream = getAssets().open("location.png");
            bmp = BitmapFactory.decodeStream(assetInStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Drawer drawer = new Drawer(context, map,bmp);

        relativeLayout.addView(drawer);


        //drawer.mapFixedPoint(new GeoPoint(0.0, 0.0), map, relativeLayout, cacheNetController);

        //creating a modified location listener
        Locator location = new Locator(map, drawer, relativeLayout);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, location); // requesting location updates

        Button recache = (Button) findViewById(R.id.recache);
        recache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlTileWriter sqlTileWriter = new SqlTileWriter();
                map.getTileProvider().clearTileCache();
                sqlTileWriter.purgeCache(map.getTileProvider().getTileSource().name());
            }
        });

        //when the "center" button is clicked, we center on the user's location (from Locator class)
        Button centerOnLocation = (Button) findViewById(R.id.findme);
        map.setOnTouchListener(new MapView.OnTouchListener() { // this is a hacky way to get the coordinates of a click on the map
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                map.onTouchEvent(event); // this is needed to make the map move
                map.invalidate();
                drawer.invalidate();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    log("X: " + ((float) x) / displayMetrics.widthPixels + " Y: " + ((float) y) / displayMetrics.heightPixels);
                    System.out.println("X: " + ((float) x) / displayMetrics.widthPixels + " Y: " + ((float) y) / displayMetrics.heightPixels); // todo: remove this
                }

                return false; // so we quit the listener
            }
        });


        centerOnLocation.setOnClickListener(v -> {
            GeoPoint myLocation = location.getLocation();
            if (myLocation.getLatitude() != 0.0 && myLocation.getLongitude() != 0.0) {
                map.getController().animateTo(myLocation);
                map.getController().setCenter(myLocation);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        if (map != null) {
            map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (map != null) {
            map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
        }
    }
}
