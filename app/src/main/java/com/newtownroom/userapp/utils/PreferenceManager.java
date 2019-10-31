package com.newtownroom.userapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String USER_ID = "user_id";
    private static final String NAME = "name";
    private static final String IS_LOGGED_IN = "is_logged_in";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String EMAIL = "email";
    private static final String UNIQUE_ID = "uuid";

    private Context mContext;
    private SharedPreferences sharedPreferences;
    private String SP_NAME = "New.Town.Room";
    private SharedPreferences.Editor editor;

    public PreferenceManager(Context context) {
        this.mContext = context;
        sharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUserID(int userID) {
        editor.putInt(USER_ID, userID).commit();
    }

    public int getUserID() {
        return sharedPreferences.getInt(USER_ID, 0);
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

    public void setUniqueID(String uniqueID) {
        editor.putString(UNIQUE_ID, uniqueID).commit();
    }

    public String getUniqueID() {
        return sharedPreferences.getString(UNIQUE_ID, "");
    }

    public void clearSharedPrefs() {
        editor.clear().commit();
    }
}
