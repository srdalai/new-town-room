package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

public class LoginInput {

    @SerializedName("phone")
    private String phoneNumber;

    public LoginInput() {
    }

    public LoginInput(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
