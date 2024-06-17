package com.jj15.muffin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import android.view.View;
import android.widget.TextView;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/*
    * this class is used to draw pin circles on the map
    * @author: jj15
 */

public class Drawer extends View {
    // below we are creating variables for our paint
    Paint otherPaint, outerPaint, textPaint;
    MapView map;
    public Float i;
    float s1;
    float radius=35;
    GeoPoint myLocation;
    Bitmap locationBMP;
    Point tmp;
    public Drawer(Context context, MapView map, Bitmap loc) {
        super(context);
        this.map = map;
        textPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        textPaint.setColor(Color.WHITE);

        textPaint.setTextSize(pxFromDp(context, 24));
        outerPaint = new Paint();
        outerPaint.setStyle(Paint.Style.FILL);
        outerPaint.setColor(getResources().getColor(R.color.purple_200));
        otherPaint = new Paint();
        i=1f;

        locationBMP = loc;
    }
    // below method is use to generate px from DP.
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
    public void setMyLoc(GeoPoint location){
        myLocation = location;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(map != null) { // failsafe if drawer is called before map is loaded
            if(myLocation != null) {
                tmp = map.getProjection().toPixels(myLocation, tmp);
                canvas.drawBitmap(locationBMP, tmp.x, tmp.y, otherPaint);
            }
        }
        invalidate();
    }
}
