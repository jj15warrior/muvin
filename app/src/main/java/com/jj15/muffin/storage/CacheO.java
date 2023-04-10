package com.jj15.muffin.storage;

import com.jj15.muffin.structures.PinMinimal;

import java.util.ArrayList;

/*
 * used to store info about pins in RAM
 * TODO: move to a database, might be local, just keep it out of RAM because caching thousands of images is a bad idea
 *
 * i suppose it is temporary because CacheNetController should handle that
 *
 * @author jj15
 */

public class CacheO {
    public ArrayList<PinMinimal> pins;
    public ArrayList<PinMinimal> favorites;
    CacheO() {
        pins = new ArrayList<PinMinimal>();
        favorites = new ArrayList<PinMinimal>();
    }
}
