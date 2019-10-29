package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

public class SignUpInputModel {

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phoneNumber;

    @SerializedName("email")
    private String emailAddress;

    @SerializedName("lat")
    private String latitude;

    @SerializedName("long")
    private String longitude;

    public SignUpInputModel() {
    }

    public SignUpInputModel(String name, String phoneNumber, String emailAddress, String latitude, String longitude) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
