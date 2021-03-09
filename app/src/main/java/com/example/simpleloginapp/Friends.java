package com.example.simpleloginapp;

import com.google.android.gms.maps.model.LatLng;

public class Friends {
    private String username;
    private LatLng latLng;

    public Friends(){
        username = null;
        latLng = null;
    }

    public Friends(String username){
        this.username = username;
        latLng = null;
    }

    public Friends(String username, LatLng latLng){
        this.username = username;
        this.latLng = latLng;
    }

    public String getUsername() {
        return username;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String toString(){
        return "Friend - Username: " + username;
    }
}
