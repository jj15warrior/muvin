package com.jj15.muffin;

import android.graphics.Bitmap;
import android.graphics.Color;

public class PinMinimal {
    double lat;
    double lon;
    String name;
    String description;
    Bitmap image;
    Color aroundColor;
    String uuid;

    PinMinimal(double lat, double lon, String name, String description, Bitmap image, Color aroundColor, String uuid) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.description = description;
        this.image = image;
        this.aroundColor = aroundColor;
        this.uuid = uuid;
    }
}
