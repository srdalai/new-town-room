package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

public class OtpResponse {

    @SerializedName("code")
    private  int responseCode;

    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String message;

    @SerializedName("uniqid")
    private String uniqid;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("user_id")
    private String userID;

    public OtpResponse() {
    }

    public OtpResponse(int responseCode, String status, String message, String userID) {
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

    public String getUniqid() {
        return uniqid;
    }

    public void setUniqid(String uniqid) {
        this.uniqid = uniqid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
