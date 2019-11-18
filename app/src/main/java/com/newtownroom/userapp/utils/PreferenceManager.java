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
    private static final String IS_PAYMENT_COMPLETE = "is_payment_complete";

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
        int user_id = sharedPreferences.getInt(USER_ID, 0);
        //user_id = 19;
        return user_id;
    }

    public void setName(String name) {
        editor.putString(NAME, name).commit();
    }

    public String getName() {
        String name = sharedPreferences.getString(NAME, null);
        //name = "John Doe";
        return name;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn).commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void setPhoneNumber(String phoneNumber) {
        editor.putString(PHONE_NUMBER, phoneNumber).commit();
    }

    public String getPhoneNumber() {
        String phone = sharedPreferences.getString(PHONE_NUMBER, null);
        //phone = "9090090900";
        return phone;
    }

    public void setEmail(String email) {
        editor.putString(EMAIL, email).commit();
    }

    public String getEmail() {
        String email = sharedPreferences.getString(EMAIL, null);
        //email = "email@email.com";
        return email;
    }

    public void setUniqueID(String uniqueID) {
        editor.putString(UNIQUE_ID, uniqueID).commit();
    }

    public String getUniqueID() {
        return sharedPreferences.getString(UNIQUE_ID, "");
    }

    public void setIsPaymentComplete(boolean value) {
        editor.putBoolean(IS_PAYMENT_COMPLETE, value).commit();
    }

    public boolean isPaymentComplete() {
        return sharedPreferences.getBoolean(IS_PAYMENT_COMPLETE, true);
    }

    public void clearSharedPrefs() {
        editor.clear().commit();
    }
}
