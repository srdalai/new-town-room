package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

public class OtpResponseModel {

    @SerializedName("code")
    private  int responseCode;

    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String message;

    @SerializedName("user_id")
    private String userID;

    public OtpResponseModel() {
    }

    public OtpResponseModel(int responseCode, String status, String message, String userID) {
        this.responseCode = responseCode;
        this.status = status;
        this.message = message;
        this.userID = userID;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
