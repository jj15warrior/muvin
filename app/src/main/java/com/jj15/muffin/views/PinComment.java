package com.jj15.muffin.views;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jj15.muffin.CacheNetController;
import com.jj15.muffin.R;
import com.jj15.muffin.structures.User;

import java.util.ArrayList;

/*
 * fragment for displaying single comment.
 * shows username, pfp, and comment text.
 * TODO: load images in comments. i don't know how to do that yet
 * @author jj15
 */

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
        RelativeLayout pfpV = view.findViewById(R.id.pfpV);
        return view;
    }


}
