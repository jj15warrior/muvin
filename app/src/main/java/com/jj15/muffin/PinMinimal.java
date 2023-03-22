package com.jj15.muffin;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.Nullable;

public class PinMinimal {
    double lat;
    double lon;
    String name;
    String description;
    Bitmap image;
    Paint aroundColor;
    String uuid;

    PinMinimal(double lat, double lon, String name, String description, @Nullable Bitmap image, Paint aroundColor, String uuid) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.description = description;
        this.image = image;
        this.aroundColor = aroundColor;
        this.uuid = uuid;
    }


}
