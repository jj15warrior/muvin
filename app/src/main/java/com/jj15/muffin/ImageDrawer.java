package com.jj15.muffin;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.jj15.muffin.structures.ImgInfo;

import java.util.ArrayList;

/*
 * used to draw images on user views. (ex. profile pictures)
 * TODO: handle scaling
 * @author jj15
*/

public class ImageDrawer extends View {
    ArrayList<ImgInfo> images;
    public ImageDrawer(Context context){
        super(context);
    }
    public void clear(){
        images.clear();
        invalidate();
    }
    public void addImage(ImgInfo image){
        images.add(image);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        invalidate();
    }

}
