package com.dia.hereyourlocation.apiresponse.application;

import android.location.Location;
import android.support.multidex.MultiDexApplication;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by abishek on 2/14/2017.
 */

public class ParselApplication extends MultiDexApplication {

    private boolean isLoggedIn;

    Location currentLocation;
    HashMap<String, String> currentAddress;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public HashMap<String, String> getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(HashMap<String, String> currentAddress) {
        this.currentAddress = currentAddress;
    }
}

