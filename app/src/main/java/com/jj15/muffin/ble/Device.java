package com.jj15.muffin.ble;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
class Device{
    @ColumnInfo(name = "mac")
    String mac;

    @ColumnInfo(name = "advertiseFlags")
    int advertiseFlags;
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
    @ColumnInfo(name = "eventType")
    Integer evenType;
    @ColumnInfo(name = "advertisingSid")
    Integer advertisingSid;

    @ColumnInfo(name = "periodicAdvInterval")
    Integer periodicAdvInterval;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getAdvertiseFlags() {
        return advertiseFlags;
    }

    public void setAdvertiseFlags(int advertiseFlags) {
        this.advertiseFlags = advertiseFlags;
    }

    public List<String> getServiceUuids() {
        return serviceUuids;
    }

    public void setServiceUuids(List<String> serviceUuids) {
        this.serviceUuids = serviceUuids;
    }

    public List<String> getServiceSolicitationUuids() {
        return serviceSolicitationUuids;
    }

    public void setServiceSolicitationUuids(List<String> serviceSolicitationUuids) {
        this.serviceSolicitationUuids = serviceSolicitationUuids;
    }

    public Map<Integer, byte[]> getManufacturerData() {
        return manufacturerData;
    }

    public void setManufacturerData(Map<Integer, byte[]> manufacturerData) {
        this.manufacturerData = manufacturerData;
    }

    public Map<String, byte[]> getServiceData() {
        return serviceData;
    }

    public void setServiceData(Map<String, byte[]> serviceData) {
        this.serviceData = serviceData;
    }

    public Integer getTxPower() {
        return txPower;
    }

    public void setTxPower(Integer txPower) {
        this.txPower = txPower;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getEvenType() {
        return evenType;
    }

    public void setEvenType(Integer evenType) {
        this.evenType = evenType;
    }

    public Integer getAdvertisingSid() {
        return advertisingSid;
    }

    public void setAdvertisingSid(Integer advertisingSid) {
        this.advertisingSid = advertisingSid;
    }

    public Integer getPeriodicAdvInterval() {
        return periodicAdvInterval;
    }

    public void setPeriodicAdvInterval(Integer periodicAdvInterval) {
        this.periodicAdvInterval = periodicAdvInterval;
    }

    public Device() {
        serviceUuids = new ArrayList<>();
        serviceSolicitationUuids = new ArrayList<>();
        serviceData = new HashMap<>();
        manufacturerData = new HashMap<>();
    }
}

