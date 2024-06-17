package com.jj15.muffin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

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
    GeoPoint GeoPoint_global;
    public Float i;
    float s1;
    float radius=35;
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
        GeoPoint_global = new GeoPoint(52.2068, 21.0495);
        i=1f;
    }
    // below method is use to generate px from DP.
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


    public void mapFixedPoint(GeoPoint point) {
        this.GeoPoint_global = point;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(map != null) { // failsafe if drawer is called before map is loaded

        }
        invalidate();
    }
}
