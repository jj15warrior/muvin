package com.jj15.muffin.ble;

import android.annotation.SuppressLint;
import android.bluetooth.le.ScanResult;
import android.os.ParcelUuid;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ParseDispatcher {
    Stack<ScanResult> st;
    int threadsRunning;
    int maxThreads = 20;
    List<Thread> threads;

    Thread parserThread = new Thread(){
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
    @SuppressLint({ "MissingPermission"})
    void pushToDB(ScanResult result){
        Device d = new Device();
        d.advertiseFlags = new Integer[]{result.getScanRecord().getAdvertiseFlags()};
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

        DatabaseManager.write(d);
    }
    ParseDispatcher(){
        st = new Stack<ScanResult>();
        threads = new ArrayList<>();
        threadsRunning=0;
    }
    synchronized boolean checkQEmpty(){
        return st.isEmpty();
    }
    synchronized ScanResult pollQ(){
        return st.pop();
    }
    void add(ScanResult result){
        st.add(result);
        threadStateUpdate();
    }
    void threadStateUpdate(){
        if(threadsRunning == maxThreads){
            // all threads are maxed out, the queue will shrink if the device is capable enough
            System.out.println("all threads firing");
        }else{
            threads.add(parserThread);
            threads.get(threadsRunning).run();
            threadsRunning++;
        }
    }
}
