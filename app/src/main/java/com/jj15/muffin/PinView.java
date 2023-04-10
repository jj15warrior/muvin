package com.jj15.muffin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PinView extends Fragment{
    String uuid;
    CacheNetController cacheNetController;

    public PinView(String uuid, CacheNetController cacheNetController) {
        this.uuid = uuid;
        this.cacheNetController = cacheNetController;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pinview, container, false);

        FragmentManager fragmentManager = getParentFragmentManager();

        ScrollView scrollView = view.findViewById(R.id.scrollView1);
        PinComment pinComment = new PinComment();
        pinComment.init(uuid, cacheNetController);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(pinComment.onCreateView(inflater, container, savedInstanceState));
        linearLayout.addView(pinComment.onCreateView(inflater, container, savedInstanceState));
        linearLayout.addView(pinComment.onCreateView(inflater, container, savedInstanceState));
        linearLayout.addView(pinComment.onCreateView(inflater, container, savedInstanceState));
        scrollView.addView(linearLayout);
        return view;
    }


}
