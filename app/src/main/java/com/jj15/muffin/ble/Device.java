package com.jj15.muffin.ble;

import android.bluetooth.le.ScanResult;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Entity
class Device{
    @ColumnInfo(name = "mac")
    String mac;

    @ColumnInfo(name = "advertiseFlags")
    Integer[] advertiseFlags;
    @ColumnInfo(name = "serviceUuids")
    List<String> serviceUuids;
    @ColumnInfo(name = "serviceSolicitationUuids")
    List<String> serviceSolicitationUuids;
    @ColumnInfo(name = "manufacturerData")
    Map<Integer, byte[]> manufacturerData;
    @ColumnInfo(name = "serviceData")
    Map<String, byte[]> serviceData;
    @ColumnInfo(name = "txPower")
    Integer txPower;
    @ColumnInfo(name = "deviceName")
    String deviceName;
    @ColumnInfo(name = "rssi")
    Integer rssi;

    @PrimaryKey
    long timestamp;
    @ColumnInfo(name = "")

    Integer evenType;
    @ColumnInfo(name = "advertisingSid")
    Integer advertisingSid;

    @ColumnInfo(name = "periodicAdvInterval")
    Integer periodicAdvInterval;


    public Device() {
        serviceUuids = new ArrayList<>();
        serviceSolicitationUuids = new ArrayList<>();
        serviceData = new HashMap<>();
        manufacturerData = new HashMap<>();
    }
}

