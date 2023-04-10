package com.jj15.muffin.storage;


import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/*
    * this class is used to load assets from the assets folder
    * it is used to load the location.png and the pin images
    * it is also used to load images from the internet
    * @author jj15
 */


public class AssetLoader{
    public Bitmap myLocationBMP(Context ctx) throws IOException {
        InputStream assetInStream=ctx.getAssets().open("location.png");
        Bitmap bmp = BitmapFactory.decodeStream(assetInStream);
        return bmp;
    }
    public Bitmap fetchFromURL(String iurl, Context ctx) throws IOException {
        URL url = new URL(iurl);
        Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        return getCroppedBitmap(image);
    }
    public Bitmap fetchPinmg(String iurl, Context ctx) {
        try {
            InputStream assetInStream = ctx.getAssets().open(iurl);
            Bitmap bmp = BitmapFactory.decodeStream(assetInStream);
            return getCroppedBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        bitmap = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        return output;
    }
}
