
package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class DeleteGstResponse {

    @Expose
    private int code;
    @Expose
    private String msg;
    @Expose
    private String status;

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
