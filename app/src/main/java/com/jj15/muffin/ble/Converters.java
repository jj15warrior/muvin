package com.jj15.muffin.ble;

import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Converters {
    @TypeConverter
    public static List<String> fromStringToList(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static String fromMapb(Map<Integer, byte[]> inp){
        Gson gson = new Gson();
        String json = gson.toJson(inp);
        return json;
    }

    @TypeConverter
    public static Map<Integer, byte[]> fromStringToMapb(String inp){
        Gson gson = new Gson();
        Type t = new TypeToken<Map<Integer, byte[]>>() {}.getType();
        Map<Integer, byte[]> ret = gson.fromJson(inp,t);
        return ret;
    }

    @TypeConverter
    public static String fromMaps(Map<String, byte[]> inp){
        Gson gson = new Gson();
        String json = gson.toJson(inp);
        return json;
    }

    @TypeConverter
    public static Map<String, byte[]> fromStringToMaps(String inp){
        Gson gson = new Gson();
        Type t = new TypeToken<Map<String, byte[]>>() {}.getType();
        Map<String, byte[]> ret = gson.fromJson(inp,t);
        return ret;
    }
}