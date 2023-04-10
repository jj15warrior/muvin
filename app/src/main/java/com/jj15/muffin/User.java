package com.jj15.muffin;

import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/*
 * user class. stores user data.
 */

public class User {
    String uname;
    String email;
    Image avatar;
    ArrayList<PinMinimal> pins;
    ArrayList<PinMinimal> favorites;
    String uuid;
    Paint color;

    User(String uname, String email, Image avatar, ArrayList<PinMinimal> pins, ArrayList<PinMinimal> favorites, String uuid, Paint color) {
        this.uname = uname;
        this.email = email;
        this.avatar = avatar;
        this.pins = pins;
        this.favorites = favorites;
        this.color = color;
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
    User() {
        //TODO: implement
    }

}
