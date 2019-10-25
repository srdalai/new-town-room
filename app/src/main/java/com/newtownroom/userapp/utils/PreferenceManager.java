package com.newtownroom.userapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String USER_ID = "user_id";
    private static final String NAME = "name";
    private static final String IS_LOGGED_IN = "is_logged_in";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String EMAIL = "email";

    private Context mContext;
    private SharedPreferences sharedPreferences;
    private String SP_NAME = "New.Town.Room";
    private SharedPreferences.Editor editor;

    public PreferenceManager(Context context) {
        this.mContext = context;
        sharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUserID(String userID) {
        editor.putString(USER_ID, userID).commit();
    }

    public String getUserID() {
        return sharedPreferences.getString(USER_ID, null);
    }

    public void setName(String name) {
        editor.putString(NAME, name).commit();
    }

    public String getName() {
        return sharedPreferences.getString(NAME, null);
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn).commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, true);
    }

    public void setPhoneNumber(String phoneNumber) {
        editor.putString(PHONE_NUMBER, phoneNumber).commit();
    }

    public String getPhoneNumber() {
        return sharedPreferences.getString(PHONE_NUMBER, null);
    }

    public void setEmail(String email) {
        editor.putString(EMAIL, email).commit();
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, null);
    }

    public void clearSharedPrefs() {
        editor.clear().commit();
    }
}
