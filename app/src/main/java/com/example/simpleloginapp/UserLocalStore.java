package com.example.simpleloginapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.core.UserWriteRecord;

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", user.getUsername());
        spEditor.putString("password", user.getPassword());
        spEditor.putString("name", user.getName());
        spEditor.putInt("familyID", user.getFamilyID());
        spEditor.commit();
    }

    public User getLoggedInUser(){
        String userName = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String name = userLocalDatabase.getString("name", "");
        int familyID = userLocalDatabase.getInt("familyID", 0);

        User storedUser = new User(name, userName, password, familyID);

        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn", false) == true){
            return true;
        }
        else{
            return false;
        }

    }
}
