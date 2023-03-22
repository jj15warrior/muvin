package com.jj15.muffin;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

//todo: remove nullable annotations -> testing only

public class CacheNetController {
    CacheO cache = new CacheO();
    private User me;
    CacheNetController() {
        this.setMe(new User());
    }
    public PinFull getPinFull(String uuid,@Nullable boolean offlinemode) {
        //TODO: implement
        return null;
    }
    public PinMinimal getPinMinimal(String uuid,@Nullable boolean offlinemode) {
        if(offlinemode) {
            for(PinMinimal pin : cache.pins) {
                if(pin.uuid.equals(uuid)) {
                    return pin; //TODO: maybe optimize in online mode
                }
            }
        }
        return null;

    }
    public User getUser(String uuid,@Nullable boolean testmode) {
        return null;
    }
    public User me(@Nullable boolean testmode) {
        return null;
    }
    public int addPin(PinMinimal pin, @Nullable boolean offlinemode) {
        if(offlinemode){
            cache.pins.add(pin);
            return 1;
        }
        return 0; //status code
    }
    public int addComment(PinComment comment, String pinUUID, @Nullable boolean offlinemode) {
        return 0; //status code
    }
    public int addFavorite(String pinUUID, @Nullable boolean offlinemode) {
        return 0; //status code
    }
    public int removeFavorite(String pinUUID, @Nullable boolean offlinemode) {
        return 0; //status code
    }
    private boolean saveBitmap(Bitmap bmp, String filename) {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean syncCache(@Nullable boolean testmode) {
        if(testmode) return true; //TODO: implement

        //probably hardest part -> sync cache with server -> server cache overwrites local cache
        // maybe use hashing?
        //TODO: implement
        return false; //should be true if successful
    }
    public ArrayList<PinMinimal> getNearbyPins(double lat, double lon, double radius,@Nullable boolean testmode) {
        return null;
    }

    public ArrayList<PinMinimal> getAllPins(@Nullable boolean offlinemode) {
        if(offlinemode) {
            return cache.pins;
        }
        return null;
    }

    public ArrayList<PinMinimal> getFavorites(@Nullable boolean offlinemode) {
        return null;
    }
    public ArrayList<PinMinimal> getMyPins(@Nullable boolean offlinemode) {
        return null;
    }
    public ArrayList<PinComment> getComments(String pinUUID,@Nullable boolean testmode, @Nullable boolean offlinemode) {
        return null;
    }
    public ArrayList<PinMinimal> search(String query, @Nullable boolean offlinemode) {
        return null;
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }
    public String prefetchUuid(@Nullable boolean testmode) {
        if(testmode) {
            return "testmode-uuid-1234";
        }
        return null;
    }
}
