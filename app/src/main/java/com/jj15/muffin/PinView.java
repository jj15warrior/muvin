package com.jj15.muffin;

import androidx.fragment.app.Fragment;

public class PinView extends Fragment {
    String uuid;
    CacheNetController cacheNetController;
    RootFragment mainActivity;

    public PinView(String uuid, CacheNetController cacheNetController, RootFragment rootFragment) {
        super(R.layout.pinview);
        this.uuid = uuid;
        this.cacheNetController = cacheNetController;
        this.mainActivity = rootFragment;
    }
    void run(){
        getActivity().navigateUpTo(getActivity().getIntent());
    }
}
