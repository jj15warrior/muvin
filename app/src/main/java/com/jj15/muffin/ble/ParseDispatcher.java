package com.jj15.muffin.ble;

import android.annotation.SuppressLint;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;
import android.util.SparseArray;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ParseDispatcher {
    DatabaseManager db;
    Stack<ScanResult> st;
    int threadsRunning;
    int maxThreads = 20;
    List<Thread> threads;

    Thread getInstance(){
        Thread parserThread = new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void run() {

                while (true) {
                    if (checkQEmpty()) {
                        threadsRunning--;
                        return;
                    } else {
                        ScanResult r = pollQ();
                        pushToDB(r);
                    }
                }
            }
        };
        return parserThread;
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint({ "MissingPermission"})
    void pushToDB(ScanResult result){
        Device d = new Device();
        d.advertiseFlags = result.getScanRecord().getAdvertiseFlags();
        d.advertisingSid = result.getAdvertisingSid();
        d.mac = result.getDevice().getAddress();
        d.deviceName = result.getDevice().getName();
        d.rssi = result.getRssi();
        Map<ParcelUuid, byte[]> tmpsrvdata = result.getScanRecord().getServiceData();
        for(ParcelUuid uuid : tmpsrvdata.keySet()){
            d.serviceData.put(uuid.getUuid().toString(), tmpsrvdata.get(uuid));
        }
        d.txPower = result.getTxPower();
        d.timestamp = result.getTimestampNanos();
        d.evenType = result.getDevice().getType();
        d.periodicAdvInterval = result.getPeriodicAdvertisingInterval();
        List<ParcelUuid> tmpserviceuuids = result.getScanRecord().getServiceUuids();
        if(tmpserviceuuids != null) {
            for (ParcelUuid parcelUuid : tmpserviceuuids) {
                d.serviceUuids.add(parcelUuid.getUuid().toString());
            }
        }
        tmpserviceuuids = result.getScanRecord().getServiceSolicitationUuids();
        if(tmpserviceuuids != null) {
            for (ParcelUuid parcelUuid : tmpserviceuuids) {
                d.serviceSolicitationUuids.add(parcelUuid.getUuid().toString());
            }
        }
        SparseArray<byte[]> tmpmansped = result.getScanRecord().getManufacturerSpecificData();
        for(int i=0;i<tmpmansped.size();i++){
            d.manufacturerData.put(tmpmansped.keyAt(i), tmpmansped.valueAt(i));
        }

        db.write(d);
    }
    public ParseDispatcher(Context ctx){
        st = new Stack<ScanResult>();
        threads = new ArrayList<>();
        threadsRunning=0;
        DeviceRoomDatabase deviceRoomDatabase = DeviceRoomDatabase.getDatabase(ctx);
        db = new DatabaseManager(deviceRoomDatabase);
    }
    synchronized boolean checkQEmpty(){
        return st.isEmpty();
    }
    synchronized ScanResult pollQ(){
        return st.pop();
    }
    public void add(ScanResult result){
        st.add(result);
        threadStateUpdate();
    }
    void threadStateUpdate(){
        System.out.print(st.size());
        System.out.print(" ");
        System.out.println(threadsRunning);
        for(int j=0;j<threads.size();j++) {
            for (int i = 0; i < threads.size(); i++) {
                if (threads.get(i).getState() == Thread.State.TERMINATED) {
                    threads.remove(i);
                    threadsRunning--;
                    break;
                }
            }
        }
        if(threadsRunning == maxThreads){
            // all threads are maxed out, the queue will shrink if the device is capable enough
            System.out.println("all threads firing");
        }else{
            if(threadsRunning<0){
                threadsRunning=0;
            }
            threads.add(getInstance());
            if(threads.get(threadsRunning).getState() == Thread.State.TERMINATED){
                threads.remove(threadsRunning);
                threads.add(getInstance());
            }
            try {
                threads.get(threadsRunning).start();
            }catch (IllegalThreadStateException e){
                System.out.println("race condition caught");
                threads.remove(threadsRunning);
                threads.add(getInstance());
            }
            threadsRunning++;
        }
    }
}
