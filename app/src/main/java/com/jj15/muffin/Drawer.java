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

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.util.ArrayList;

import kotlin.Pair;

public class Drawer extends View {
    // below we are creating variables for our paint
    Paint otherPaint, outerPaint, textPaint;
    public boolean offlinetesting = true;
    public Point points_global = new Point();
    public ArrayList<Pair<Point,String>> centers = new ArrayList<>();

    Canvas canvas_global = new Canvas();
    private MapView map = null;
    private RelativeLayout relativeLayout = null;
    private GeoPoint GeoPoints_global;
    private CacheNetController cacheNetController;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
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


    public void mapFixedPoint(GeoPoint point, MapView map, RelativeLayout relativeLayout, CacheNetController cacheNetController) {
        this.map = map;
        this.relativeLayout = relativeLayout;
        this.GeoPoints_global = point;
        this.cacheNetController = cacheNetController;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(map != null && relativeLayout != null) {
            points_global = map.getProjection().toPixels(GeoPoints_global, null);
            AssetLoader assetLoader = new AssetLoader();
            //canvas.drawCircle(points_global.x, points_global.y, 20, outerPaint);
            Bitmap bmp = null;
            try {
                bmp = assetLoader.myLocationBMP(getContext());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            canvas.drawBitmap(bmp, points_global.x-25, points_global.y-25, null);

            centers.clear();
            for(PinMinimal pin : cacheNetController.getAllPins(offlinetesting)) {
                GeoPoint pinPoint = new GeoPoint(pin.lat, pin.lon);
                Point pinPointPixels = map.getProjection().toPixels(pinPoint, null);
                otherPaint = cacheNetController.getPinMinimal(pin.uuid, offlinetesting).aroundColor;
                canvas.drawCircle(pinPointPixels.x, pinPointPixels.y, 35, otherPaint);
                canvas.drawBitmap(assetLoader.fetchPinmg("testico.png", getContext()), pinPointPixels.x-30, pinPointPixels.y-30, otherPaint);
                Pair<Point,String> par = new Pair<>(pinPointPixels, pin.uuid);
                centers.add(par);
            }
        }
        invalidate();
    }
}
