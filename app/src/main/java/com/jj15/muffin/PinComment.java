package com.jj15.muffin;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class PinComment{
    User author;
    String text;
    ArrayList<Image> image;

    String uuid;
    CacheNetController cacheNetController;

    public void init(String uuid, CacheNetController cacheNetController) {
        this.uuid = uuid;
        this.cacheNetController = cacheNetController;
    }

    public PinComment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.single_comment_view, container, false);
        return view;
    }


}
