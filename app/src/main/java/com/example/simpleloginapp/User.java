package com.example.simpleloginapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class User {
    private String name;
    private String username;
    private String password;
    private LatLng latLng;
    private int familyID;

    public User (){
        this.name = null;
        this.username = null;
        this.password = null;
        latLng = null;
        familyID = 0;
    }

    public User (String username, String password){
        this.name = null;
        this.username = username;
        this.password = password;
        latLng = null;
        this.familyID = 0;
    }

    public User (String name, String username, String password, int familyID){
        this.name = name;
        this.username = username;
        this.password = password;
        latLng = null;
        this.familyID = familyID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFamilyID(int familyID) {
        this.familyID = familyID;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public int getFamilyID() {
        return familyID;
    }

    public String toString(){
        return "Name: " + name + " Username: " + username + " Password: " + password + " Family ID: " + familyID;
    }
}
