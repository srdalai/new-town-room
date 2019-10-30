
package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CheckAvailResponse {

    @SerializedName("available_status")
    private int availableStatus;
    @Expose
    private int code;
    @Expose
    private String msg;
    @Expose
    private String status;

    public int getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(int availableStatus) {
        this.availableStatus = availableStatus;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
