package com.jj15.muffin;


import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;


public class AssetLoader{
    public Bitmap myLocationBMP(Context ctx) throws IOException {
        InputStream assetInStream=ctx.getAssets().open("location.png");
        Bitmap bmp = BitmapFactory.decodeStream(assetInStream);
        return bmp;
    }
}
