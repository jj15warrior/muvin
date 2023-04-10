package com.jj15.muffin;

import android.graphics.Bitmap;
import android.graphics.Point;

import kotlin.Pair;

/**
 * this class is used to store information about images that are to be drawn on Views like PinView
 * or any other "user" view
 * @author jj15
 */

public class ImgInfo {
    public String url;
    public Bitmap bitmap;
    public Point start;
    public Pair<Integer, Integer> dimensions;

    public ImgInfo(Bitmap bmp, String url) {
        this.url = url;
        this.bitmap = bmp;
    }
}
