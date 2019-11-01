
package com.newtownroom.userapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Coupon {

    @Expose
    private String amount;
    @Expose
    private String code;
    @SerializedName("created_date")
    private String createdDate;
    @Expose
    private String description;
    @Expose
    private String id;
    @Expose
    private String type;

    public Coupon() {
    }

    public Coupon(String amount, String code, String type) {
        this.amount = amount;
        this.code = code;
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
