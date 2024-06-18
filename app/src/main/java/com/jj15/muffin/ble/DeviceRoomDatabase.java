package com.jj15.muffin.ble;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Device.class},version = 1)
@TypeConverters({Converters.class})
public abstract class DeviceRoomDatabase extends RoomDatabase {
    public abstract DeviceDao deviceDao();

    private static volatile DeviceRoomDatabase deviceRoomDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DeviceRoomDatabase getDatabase(final Context context) {
        if (deviceRoomDatabase == null) {
            synchronized (DeviceRoomDatabase.class) {
                if (deviceRoomDatabase == null) {
                    deviceRoomDatabase = Room.databaseBuilder(context.getApplicationContext(), DeviceRoomDatabase.class ,"devices").build();
                }
            }
        }
        return deviceRoomDatabase;
    }
}
