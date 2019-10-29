package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

public class LoginInputModel {

    @SerializedName("phone")
    private String phoneNumber;

    public LoginInputModel() {
    }

    public LoginInputModel(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
