package com.jj15.muffin;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.jj15.muffin.ble.GoogleLocationWrapper;

@RequiresApi(api = Build.VERSION_CODES.S)
public class MainActivity extends AppCompatActivity {

    public final String[] permissions = {BLUETOOTH, BLUETOOTH_SCAN, BLUETOOTH_CONNECT, BLUETOOTH_SCAN, BLUETOOTH_ADMIN, ACCESS_BACKGROUND_LOCATION, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION,WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        context = getBaseContext();
        getPermissions();
    }
    public Context context;

    public void t(String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }


    private int last_perm_call = 0;
    private void getPermissions(){
        if(last_perm_call == permissions.length){
            System.out.println("all perms accepted");
            afterPerms();
            return;
        }
        String to_request = permissions[last_perm_call];
        System.out.println(to_request);
        last_perm_call++;
        if (ActivityCompat.checkSelfPermission(context, to_request) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(to_request);
        }else{
            System.out.println("perm skip");
        }
        getPermissions();
    }
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                getPermissions();
            });
    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION,false);
                        if (fineLocationGranted != null && fineLocationGranted) {
                            System.out.println("location granted");
                            afterLoc();
                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            System.out.println("please grant precise location permissions and restart the app");
                            t("please grant precise location permissions and restart the app");
                        } else {
                            System.out.println("please grant precise location permissions and restart the app");
                            t("please grant precise location permissions and restart the app");
                        }
                    }
            );
    void afterLoc(){
        if(canToggleGPS()){
            System.out.println("gps power-toggle available");
            turnGPSOn();
        }else{
            System.out.println("gps powertoggle patched, using manual method");
            Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
            intent.putExtra("enabled", true);
            sendBroadcast(intent);
        }
        Intent intent = new Intent(this, GoogleLocationWrapper.class);
        System.out.println("switching activity to google's");
        startActivity(intent);
    }
    void afterPerms(){
        System.out.println("location perm init");
        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }
    private boolean canToggleGPS() {
        PackageManager pacman = getPackageManager();
        PackageInfo pacInfo = null;

        try {
            pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
        } catch (PackageManager.NameNotFoundException e) {
            return false; //package not found
        }

        if(pacInfo != null){
            for(ActivityInfo actInfo : pacInfo.receivers){
                //test if recevier is exported. if so, we can toggle GPS.
                if(actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider") && actInfo.exported){
                    return true;
                }
            }
        }

        return false; //default
    }
    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }
}
