package com.jj15.muffin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import com.jj15.muffin.storage.AssetLoader;
import com.jj15.muffin.structures.PinMinimal;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.util.ArrayList;

import kotlin.Pair;

/*
    * this class is used to draw pin circles on the map
    * @author: jj15
 */

public class Drawer extends View {
    // below we are creating variables for our paint
    Paint otherPaint, outerPaint, textPaint;
    public boolean offlinetesting = true;
    public Point points_global = new Point();
    public ArrayList<Pair<Point,String>> centers = new ArrayList<>();
    MapView map;
    GeoPoint GeoPoint_global;
    CacheNetController cacheNetController;

    public Drawer(Context context, MapView map) {
        super(context);
        this.map = map;
        textPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        textPaint.setColor(Color.WHITE);

        textPaint.setTextSize(pxFromDp(context, 24));
        outerPaint = new Paint();
        outerPaint.setStyle(Paint.Style.FILL);
        outerPaint.setColor(getResources().getColor(R.color.purple_200));
        otherPaint = new Paint();
    }


    // below method is use to generate px from DP.
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


    public void mapFixedPoint(GeoPoint point, CacheNetController cacheNetController) {
        this.GeoPoint_global = point;
        this.cacheNetController = cacheNetController;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(map != null && GeoPoint_global != null && cacheNetController != null) { // failsafe if drawer is called before map is loaded
            AssetLoader assetLoader = new AssetLoader();
            if(GeoPoint_global.getLatitude()!=0.0 && GeoPoint_global.getLatitude()!=0.0) { // check if the pin in not the default pin
                points_global = map.getProjection().toPixels(GeoPoint_global, null); // convert user coords to pixels
                canvas.drawCircle(points_global.x, points_global.y, 20, otherPaint); // draw the circle
                Bitmap bmp = null;
                try {
                    bmp = assetLoader.myLocationBMP(getContext()); // load crosshair image from assets
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                canvas.drawBitmap(bmp, points_global.x - 25, points_global.y - 25, outerPaint); // draw the image in user position
            }
            centers.clear(); // clear the list of pin centers to avoid duplicates

            for(PinMinimal pin : cacheNetController.getAllPins(offlinetesting)) { // iterate through all pins TODO: after writing CacheNetController, change this to getAllPins()
                GeoPoint pinPoint = new GeoPoint(pin.lat, pin.lon); // create a geopoint from the pin coords
                Point pinPointPixels = map.getProjection().toPixels(pinPoint, null); // convert pin coords to pixels
                otherPaint = cacheNetController.getPinMinimal(pin.uuid, offlinetesting).aroundColor; // get the color of the pin
                canvas.drawCircle(pinPointPixels.x, pinPointPixels.y, 35, otherPaint); // draw the pin circle
                canvas.drawBitmap(assetLoader.fetchPinmg("testico.png", getContext()), pinPointPixels.x-30, pinPointPixels.y-30, otherPaint);
                // draw the pin image ^^^

                Pair<Point,String> par = new Pair<>(pinPointPixels, pin.uuid); // create a pair of the pin center and the pin uuid for exporting
                centers.add(par); // add the pair to the list of pin centers
            }
        }
        invalidate();

    }
}
