package com.jj15.muffin;
import com.jj15.muffin.*;

import java.util.ArrayList;

public class CacheNetController {
    public PinFull getPinFull(String uuid) {
        //TODO: implement
        return null;
    }
    public PinMinimal getPinMinimal(String uuid) {
        return null;

    }
    public User getUser(String uuid) {
        return null;
    }
    public User me() {
        return null;
    }
    public int addPin(PinMinimal pin) {
        return 0; //status code
    }
    public int addComment(PinComment comment, String pinUUID) {
        return 0; //status code
    }
    public int addFavorite(String pinUUID) {
        return 0; //status code
    }
    public int removeFavorite(String pinUUID) {
        return 0; //status code
    }
    public boolean syncCache() {
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
    public ArrayList<PinComment> getComments(String pinUUID) {
        return null;
    }

}
