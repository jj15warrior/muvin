package com.jj15.muffin;

import android.graphics.Canvas;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.List;

public class Drawer {
    public void mapFixedList(List<GeoPoint> points, Canvas canvas, MapView map) {
        for (GeoPoint point : points) {
            map.getProjection().toPixels(point, null);
            canvas.drawCircle((float) point.getLatitude(), (float) point.getLongitude(), 10, null);
            // FIXME: not tested -> to test
        }
    }
    //TODO: mapPinPoint(GeoPoint point, Canvas canvas, MapView map, CustomDescription description, CustomIcon icon)

}
