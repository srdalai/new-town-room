package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

public class OtpInput {

    @SerializedName("phone")
    private String phoneNumber;

    @SerializedName("otp")
    private String otp;

    public OtpInput() {
    }

    public OtpInput(String phoneNumber, String otp) {
        this.phoneNumber = phoneNumber;
        this.otp = otp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
