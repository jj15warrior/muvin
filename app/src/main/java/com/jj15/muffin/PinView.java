package com.jj15.muffin;

import android.app.Activity;
import android.view.View;

public class PinView extends Activity {
    String uuid;
    CacheNetController cacheNetController;
    MainActivity mainActivity;

    public PinView(String uuid, CacheNetController cacheNetController, MainActivity mainActivity) {
        this.uuid = uuid;
        this.cacheNetController = cacheNetController;
        this.mainActivity = mainActivity;
    }
    void run(){
        mainActivity.setContentView(R.layout.pinview);

    }
    @Override
    public void onBackPressed() {
        mainActivity.setContentView(R.layout.root);
    }
}
