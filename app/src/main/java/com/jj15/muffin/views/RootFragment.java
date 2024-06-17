package com.jj15.muffin.views;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jj15.muffin.Drawer;
import com.jj15.muffin.Locator;
import com.jj15.muffin.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.modules.SqlTileWriter;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;

import kotlin.Pair;

/* main view fragment */

public class RootFragment extends Fragment {
    MapView map = null;
    DisplayMetrics displayMetrics = new DisplayMetrics();

    public RootFragment(){
        super(R.layout.root);
        System.out.println("RootFragment");
    }

    @RequiresApi(api = Build.VERSION_CODES.P)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.root, container, false);
        // Initialize any views or other elements as needed


        //load/initialize the osmdroid configuration
        Context context = view.getContext();
        Activity activity = getActivity();
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //location perm check
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1); // ask for permission
            return view;
        }

        //fix osmdroid database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));



        map = view.findViewById(R.id.map);

        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        map.getController().setZoom(13.0);
        map.getController().setCenter(new GeoPoint(52.2068, 21.0495)); // center on Warsaw, if we can't get the user's location
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        displayMetrics = getResources().getDisplayMetrics();


        /*
            TODO: write a server and not push it's address to github
         */

        //initialize customview
        RelativeLayout relativeLayout = view.findViewById(R.id.idRL);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.purple_200));



        Drawer drawer = new Drawer(context,map);
        drawer.mapFixedPoint(new GeoPoint(52.2068, 21.0495));
        relativeLayout.addView(drawer);

        //drawer.mapFixedPoint(new GeoPoint(0.0, 0.0), map, relativeLayout, cacheNetController);

        //creating a modified location listener
        Locator location = new Locator(map, drawer, relativeLayout);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, location); // requesting location updates

        Button recache = (Button) view.findViewById(R.id.recache);
        recache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlTileWriter sqlTileWriter = new SqlTileWriter();
                map.getTileProvider().clearTileCache();
                sqlTileWriter.purgeCache(map.getTileProvider().getTileSource().name());
            }
        });

        //when the "center" button is clicked, we center on the user's location (from Locator class)
        Button centerOnLocation = (Button) view.findViewById(R.id.findme);
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        map.setOnTouchListener(new MapView.OnTouchListener() { // this is a hacky way to get the coordinates of a click on the map
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                map.onTouchEvent(event); // this is needed to make the map move
                map.invalidate();
                drawer.invalidate();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
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
        return view;
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
