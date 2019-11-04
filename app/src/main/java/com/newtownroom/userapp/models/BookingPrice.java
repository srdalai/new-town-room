
package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BookingPrice {

    @SerializedName("booking_price")
    private float bookingPrice;
    @SerializedName("coupon_label")
    private String couponLabel;
    @SerializedName("coupon_price")
    private float couponPrice;
    @SerializedName("coupon_type")
    private String couponType;
    @SerializedName("droped_price")
    private float droppedPrice;
    @SerializedName("payable_price")
    private float payablePrice;

    public float getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(float bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public String getCouponLabel() {
        return couponLabel;
    }

    public void setCouponLabel(String couponLabel) {
        this.couponLabel = couponLabel;
    }

    public float getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(float couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public float getDroppedPrice() {
        return droppedPrice;
    }

    public void setDroppedPrice(float droppedPrice) {
        this.droppedPrice = droppedPrice;
    }

    public float getPayablePrice() {
        return payablePrice;
    }

    public void setPayablePrice(float payablePrice) {
        this.payablePrice = payablePrice;
    }

}
