package com.jj15.muffin;
import androidx.annotation.Nullable;

import com.jj15.muffin.*;

import java.util.ArrayList;

//todo: remove nullable annotations -> testing only

public class CacheNetController {
    public PinFull getPinFull(String uuid,@Nullable boolean offlinemode) {
        //TODO: implement
        return null;
    }
    public PinMinimal getPinMinimal(String uuid,@Nullable boolean offlinemode) {
        return null;

    }
    public User getUser(String uuid,@Nullable boolean testmode) {
        return null;
    }
    public User me(@Nullable boolean testmode) {
        return null;
    }
    public int addPin(PinMinimal pin, @Nullable boolean offlinemode) {
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
    public boolean syncCache() {
        //probably hardest part -> sync cache with server -> which cache should stay in case of conflict?
        //TODO: implement
        return false; //should be true if successful
    }
    public ArrayList<PinMinimal> getNearbyPins(double lat, double lon, double radius) {
        return null;
    }
    public ArrayList<PinMinimal> getFavorites() {
        return null;
    }
    public ArrayList<PinMinimal> getMyPins() {
        return null;
    }
    public ArrayList<PinComment> getComments(String pinUUID,@Nullable boolean testmode, @Nullable boolean offlinemode) {
        return null;
    }
    public ArrayList<PinMinimal> search(String query) {
        return null;
    }

}
