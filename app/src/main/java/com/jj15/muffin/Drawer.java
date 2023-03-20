package com.jj15.muffin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Drawer extends View {
    // below we are creating variables for our paint
    Paint otherPaint, outerPaint, textPaint;

    public Point points_global = new Point();

    Canvas canvas_global = new Canvas();
    private MapView map = null;
    private RelativeLayout relativeLayout = null;
    private GeoPoint GeoPoints_global;

    public Drawer(Context context) {
        super(context);

        // on below line we are initializing our paint variable for our text
        textPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        // on below line we are setting color to it.
        textPaint.setColor(Color.WHITE);

        // on below line we are setting text size to it.
        // In Paint we have to add text size using px so
        // we have created a method where we are converting dp to pixels.
        textPaint.setTextSize(pxFromDp(context, 24));

        // on below line we are initializing our outer paint
        outerPaint = new Paint();

        // on below line we are setting style to our paint.
        outerPaint.setStyle(Paint.Style.FILL);

        // on below line we are setting color to it.
        outerPaint.setColor(getResources().getColor(R.color.purple_200));

        // on below line we are creating a display metrics
        DisplayMetrics displayMetrics = new DisplayMetrics();

        // on below line we are getting display metrics.
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        // on below line we are creating
        // a new variable for our paint
        otherPaint = new Paint();
    }

    // below method is use to generate px from DP.
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


    public void mapFixedPoint(GeoPoint point, MapView map, RelativeLayout relativeLayout) {
        this.map = map;
        this.relativeLayout = relativeLayout;
        this.GeoPoints_global = point;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(map != null && relativeLayout != null) {
            points_global = map.getProjection().toPixels(GeoPoints_global, null);
            AssetLoader assetLoader = new AssetLoader();
            //canvas.drawCircle(points_global.x, points_global.y, 20, outerPaint);
            Bitmap bmp = null;

            //catch IOException, but it is not necessary because we are sure that the file exists

            try {
                bmp = assetLoader.myLocationBMP(getContext());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            canvas.drawBitmap(bmp, points_global.x-25, points_global.y-25, null);
        }
        invalidate();
    }
}
