package com.jj15.muffin;

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
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;

import com.jj15.muffin.BetterActivityResult;
import com.jj15.muffin.ble.ParseDispatcher;

import java.util.Collections;
import java.util.List;

public class BleAdapter extends Thread {
    ParseDispatcher parseDispatcher;
    ScanFilter scanFilter;
    ScanSettings scanSettings;
    boolean scanning = false;
    Context context;

    public BleAdapter(Context ctx) {
        parseDispatcher = new ParseDispatcher();
        context = ctx;
    }
    protected BetterActivityResult<Intent, ActivityResult> activityLauncher = null;
    Thread thr = new Thread() {
        @Override
        public void start() {
            super.start();
            runscan();
        }
    };

    public void t(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public void init(BetterActivityResult<Intent, ActivityResult> al) {
        this.activityLauncher = al;
        thr.start();
    }

    @SuppressLint("MissingPermission")
    public void runscan() {

        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            t("device does not support bluetooth");
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            System.out.println("sss");
            Intent enableBtIntent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
            activityLauncher.launch(enableBtIntent, result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    System.out.println("testtest");
                    System.out.println(data.toString());
                    if(false){
                        afterChecks();
                    }
                }else{
                    System.out.println("fail");
                    System.out.println(result.toString());
                }
            });
        }else{
            afterChecks();
        }


    }
    @SuppressLint("MissingPermission")
    void afterChecks(){
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        scanSettings = new ScanSettings.Builder()
                .setScanMode(SCAN_MODE_LOW_LATENCY)
                .setMatchMode(MATCH_MODE_AGGRESSIVE)
                .setReportDelay(0)
                .build();

        bleScanner = bluetoothLeScanner;

        scanFilter = new ScanFilter.Builder().build();
        bleScanner.startScan(Collections.singletonList(scanFilter), scanSettings, scanCallback);
    }
    ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            System.out.println(errorCode);
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            parseDispatcher.add(result);
        }
    };
    private BluetoothLeScanner bleScanner;

    @SuppressLint("MissingPermission")
    public void pauseScan(){
        if(bleScanner != null) {
            bleScanner.stopScan(scanCallback);
        }
    }

    public void resumeScan(){
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        if (!bluetoothAdapter.isEnabled()) {
            System.out.println("sss");
            Intent enableBtIntent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
            activityLauncher.launch(enableBtIntent, result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    System.out.println("testtest");
                    System.out.println(data.toString());
                    if(false){
                        afterChecks();
                    }
                }else{
                    System.out.println("fail");
                    System.out.println(result.toString());
                }
            });
        }else{
            afterChecks();
        }
    }

}
