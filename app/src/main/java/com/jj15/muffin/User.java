package com.jj15.muffin;

import android.media.Image;

import java.util.ArrayList;

public class User {
    String uname;
    String email;
    Image avatar;
    ArrayList<PinMinimal> pins;
    ArrayList<PinMinimal> favorites;
    String uuid;
    User(String uname, String email, Image avatar, ArrayList<PinMinimal> pins, ArrayList<PinMinimal> favorites, String uuid) {
        this.uname = uname;
        this.email = email;
        this.avatar = avatar;
        this.pins = pins;
        this.favorites = favorites;
        this.uuid = uuid;
    }
}
