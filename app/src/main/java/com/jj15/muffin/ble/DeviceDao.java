package com.jj15.muffin.ble;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDevice(Device dev);
    @Delete
    public void deleteDevice(Device dev);
    @Query("SELECT * FROM Device")
    public Device[] loadAllDevices();
    @Query("SELECT COUNT(mac) FROM Device")
    int getRowCount();

}

