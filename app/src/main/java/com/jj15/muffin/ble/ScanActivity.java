package com.jj15.muffin.ble;

import static android.bluetooth.le.ScanSettings.MATCH_MODE_AGGRESSIVE;
import static android.bluetooth.le.ScanSettings.SCAN_MODE_LOW_LATENCY;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jj15.muffin.R;
import com.jj15.muffin.views.RootFragment;

import java.util.Collections;
import java.util.List;

class BleAdapter extends Thread {
    ParseDispatcher parseDispatcher;
    ScanFilter scanFilter;
    ScanSettings scanSettings;
    boolean scanning = false;
    Context context;
    BleAdapter(Context ctx) {
        parseDispatcher = new ParseDispatcher();
        context = ctx;
    }
    Thread thr = new Thread(){
        public void run() {
            runscan();
        }
    };
    void init(){

    }
    public void t(String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("MissingPermission")
    public void runscan() {

        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            t("device does not support bluetooth");
            return;
        } else if (!mBluetoothAdapter.isEnabled()) {
            t("enable bluetooth");
        }
        BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        scanSettings = new ScanSettings.Builder()
                .setScanMode(SCAN_MODE_LOW_LATENCY)
                .setMatchMode(MATCH_MODE_AGGRESSIVE)
                .setReportDelay(0)
                .build();

        bleScanner = bluetoothLeScanner;

        scanFilter = new ScanFilter.Builder().build();


        System.out.println("starting scan");
        //scanBleDevice(); // for timed scans
        bleScanner.startScan(Collections.singletonList(scanFilter), scanSettings, scanCallback);
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            System.out.println(errorCode);
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            parseDispatcher.add(result);
            // TODO: parse result to Device using more standard java structures, also ScanResult is final so cannot be extended
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            // Handle scan result here
            for (ScanResult r : results) {
                //System.out.println(r.toString());
                System.out.println("batch");
            }
        }
    };
    private BluetoothLeScanner bleScanner;
    private Handler handler = new Handler();

    @SuppressLint("MissingPermission")
    private void scanBleDevice() {

        if (!scanning) {
            // Stops scanning after a predefined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanning = false;
                    bleScanner.stopScan(scanCallback);
                    System.out.println("stopping scan");
                }
            }, 10000);

            scanning = true;
            bleScanner.startScan(scanCallback);
        } else {
            scanning = false;
            bleScanner.stopScan(scanCallback);
        }
    }
}


public class ScanActivity extends AppCompatActivity {
    Context context;

    public void t(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("scan activity");
        context = getApplicationContext();
        BleAdapter bleAdapter = new BleAdapter(getApplicationContext());
        System.out.println("test1 before");
        bleAdapter.init();
        System.out.println("after");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = new RootFragment();
        fragmentTransaction.add(R.id.fragment_container,f);
        fragmentTransaction.commit();

        // todo: This break further code. figure out a threaded approach
    }
}
