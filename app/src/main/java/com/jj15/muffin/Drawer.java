package com.jj15.muffin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

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
    MapView map;
    RelativeLayout relativeLayout = null;
    GeoPoint GeoPoint_global;
    CacheNetController cacheNetController;

    public Drawer(Context context, MapView map, RelativeLayout relativeLayout) {
        super(context);
        this.map = map;
        this.relativeLayout = relativeLayout;
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

        if(map != null && relativeLayout != null && GeoPoint_global != null && cacheNetController != null) {
            AssetLoader assetLoader = new AssetLoader();
            if(GeoPoint_global.getLatitude()!=0.0 && GeoPoint_global.getLatitude()!=0.0) {
                points_global = map.getProjection().toPixels(GeoPoint_global, null);
                canvas.drawCircle(points_global.x, points_global.y, 20, otherPaint);
                Bitmap bmp = null;
                try {
                    bmp = assetLoader.myLocationBMP(getContext());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                canvas.drawBitmap(bmp, points_global.x - 25, points_global.y - 25, outerPaint);
            }
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
