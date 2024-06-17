package com.jj15.muffin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class UBDrawer {
    Canvas ubcanvas;
    Bitmap bitmap;
    Paint otherPaint, outerPaint, textPaint;
    MapView map;
    GeoPoint GeoPoint_global;
    public Float i;
    float s1;
    float radius=35;
    public UBDrawer(){
        ubcanvas = new Canvas();
        this.map = map;
        textPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        textPaint.setColor(Color.WHITE);

        outerPaint = new Paint();
        outerPaint.setStyle(Paint.Style.FILL);
        outerPaint.setARGB(255,255,0,0);
        otherPaint = new Paint();
        GeoPoint_global = new GeoPoint(52.2068, 21.0495);
        i=1f;
    }
    public void Update(){

        Point pinPointPixels = map.getProjection().toPixels(GeoPoint_global, null); // convert pin coords to pixels
        otherPaint.setARGB(255,255,0,0);
        ubcanvas.drawCircle(pinPointPixels.x, pinPointPixels.y, radius, otherPaint); // draw the pin circle
        otherPaint.setARGB(255,0,0,0);
        otherPaint.setElegantTextHeight(true);
        int j = 20,lb=0,rb=50;
        while(true){
            otherPaint.setTextSize(j);
            s1 = otherPaint.measureText(i.toString());
            if(s1 >= radius*2-15 && s1 < radius*2-5){
                System.out.print(j);
                System.out.print(" ");
                System.out.println(s1);
                break;
            }else if(s1 < radius*2-15){
                lb = j;
                j = (lb+rb)/2;
            }else if(s1 >= radius*2-5){
                rb = j;
                j = (lb+rb)/2;
            }else if(rb==lb){
                break;
            }
        }
        Rect r = new Rect();
        otherPaint.getTextBounds(i.toString(),0,i.toString().length(), r);
        System.out.println(r.height());
        ubcanvas.drawText(i.toString(),pinPointPixels.x-30,pinPointPixels.y+ ((float) r.height() /2), otherPaint);
        // draw the pin image ^^
    }
    public Canvas getCanvas(){
        return ubcanvas;
    }
}
