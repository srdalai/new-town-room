
package com.newtownroom.userapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GstModel {

    @SerializedName("user_id")
    private int userId;
    @Expose
    private int code;
    @SerializedName("gst_address")
    private String gstAddress;
    @SerializedName("gst_contact")
    private String gstContact;
    @SerializedName("gst_email")
    private String gstEmail;
    @SerializedName("gst_number")
    private String gstNumber;
    @SerializedName("legal_name")
    private String legalName;
    @Expose
    private String msg;
    @Expose
    private String status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getGstAddress() {
        return gstAddress;
    }

    public void setGstAddress(String gstAddress) {
        this.gstAddress = gstAddress;
    }

    public String getGstContact() {
        return gstContact;
    }

    public void setGstContact(String gstContact) {
        this.gstContact = gstContact;
    }

    public String getGstEmail() {
        return gstEmail;
    }

    public void setGstEmail(String gstEmail) {
        this.gstEmail = gstEmail;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
