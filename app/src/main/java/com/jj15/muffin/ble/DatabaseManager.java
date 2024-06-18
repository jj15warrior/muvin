package com.jj15.muffin.ble;

import androidx.room.Database;
import androidx.room.RoomDatabase;

public class DatabaseManager {
    DeviceRoomDatabase db;
    public void write(Device dev){ // TODO: write to a room database using a custom Device class
        db.deviceDao().insertDevice(dev);
    }
    public Device[] getAll(){
        return db.deviceDao().loadAllDevices();
    }
    DatabaseManager(DeviceRoomDatabase initdb){
        db = initdb;
    }
}