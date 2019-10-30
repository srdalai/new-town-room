package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("code")
    private  int responseCode;

    @SerializedName("status")
    private String status;

    @SerializedName("phone")
    private String phoneNumber;

    @SerializedName("msg")
    private String message;

    public LoginResponse() {

    }

    public LoginResponse(int responseCode, String status, String phoneNumber, String message) {
        this.responseCode = responseCode;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
