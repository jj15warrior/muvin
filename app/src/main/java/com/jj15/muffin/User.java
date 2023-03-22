package com.jj15.muffin;

import android.media.Image;

import androidx.annotation.Nullable;

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
    public boolean login(String uname, String password,@Nullable boolean testmode) {
        if(testmode){
            this.uname = "testuser";
            this.email = "jj15@jj15.tech";
            this.avatar = null;
            this.pins = new ArrayList<PinMinimal>();
            this.favorites = new ArrayList<PinMinimal>();
            this.uuid = "user-uuid-test-123";
            return true;
        }
        return false;
    }
    public boolean register(String uname, String password, String email) {
        return false;
    }
    public boolean logout(@Nullable boolean testmode) {
        if(testmode){
            return true;
        }
        return false; //TODO: implement

    }

}
