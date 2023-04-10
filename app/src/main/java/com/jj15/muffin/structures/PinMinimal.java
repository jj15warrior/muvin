package com.jj15.muffin.structures;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.Nullable;

public class PinMinimal {
    public double lat;
    public double lon;
    String name;
    String description;
    Bitmap image;
    public Paint aroundColor;
    public String uuid;

    public PinMinimal(double lat, double lon, String name, String description, @Nullable Bitmap image, Paint aroundColor, String uuid) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.description = description;
        this.image = image;
        this.aroundColor = aroundColor;
        this.uuid = uuid;
    }


}
