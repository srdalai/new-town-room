
package com.newtownroom.userapp.restmodels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.newtownroom.userapp.models.Coupon;

@SuppressWarnings("unused")
public class CouponsOutput {

    @Expose
    private Long code;
    @Expose
    private ArrayList<Coupon> coupons;
    @Expose
    private String msg;
    @Expose
    private String status;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(ArrayList<Coupon> coupons) {
        this.coupons = coupons;
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
