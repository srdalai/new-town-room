
package com.newtownroom.userapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BookingData {

    @SerializedName("applied_coupon")
    private String appliedCoupon;

    @SerializedName("booked_price")
    private String bookedPrice;

    @SerializedName("booking_status")
    private String bookingStatus;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("discount_price")
    private String discountPrice;

    @Expose
    private String excerpt;

    @SerializedName("extra_service_price")
    private String extraServicePrice;

    @SerializedName("has_modify")
    private String hasModify;

    @SerializedName("hotel_checkin")
    private Object hotelCheckin;

    @SerializedName("hotel_checkout")
    private Object hotelCheckout;

    @SerializedName("hotel_id")
    private String hotelId;

    @Expose
    private String id;

    @Expose
    private String location;

    @Expose
    private String nights;

    @SerializedName("pay_status")
    private String payStatus;

    @SerializedName("pay_type")
    private String payType;

    @SerializedName("payable_price")
    private String payablePrice;

    @Expose
    private String slug;

    @Expose
    private String title;

    @SerializedName("total_guest")
    private String totalGuest;

    @SerializedName("total_room")
    private String totalRoom;

    @Expose
    private String uniqid;

    @SerializedName("user_checkin")
    private String userCheckin;

    @SerializedName("user_checkout")
    private String userCheckout;

    @SerializedName("user_id")
    private String userId;

    public String getAppliedCoupon() {
        return appliedCoupon;
    }

    public void setAppliedCoupon(String appliedCoupon) {
        this.appliedCoupon = appliedCoupon;
    }

    public String getBookedPrice() {
        return bookedPrice;
    }

    public void setBookedPrice(String bookedPrice) {
        this.bookedPrice = bookedPrice;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getExtraServicePrice() {
        return extraServicePrice;
    }

    public void setExtraServicePrice(String extraServicePrice) {
        this.extraServicePrice = extraServicePrice;
    }

    public String getHasModify() {
        return hasModify;
    }

    public void setHasModify(String hasModify) {
        this.hasModify = hasModify;
    }

    public Object getHotelCheckin() {
        return hotelCheckin;
    }

    public void setHotelCheckin(Object hotelCheckin) {
        this.hotelCheckin = hotelCheckin;
    }

    public Object getHotelCheckout() {
        return hotelCheckout;
    }

    public void setHotelCheckout(Object hotelCheckout) {
        this.hotelCheckout = hotelCheckout;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNights() {
        return nights;
    }

    public void setNights(String nights) {
        this.nights = nights;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayablePrice() {
        return payablePrice;
    }

    public void setPayablePrice(String payablePrice) {
        this.payablePrice = payablePrice;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalGuest() {
        return totalGuest;
    }

    public void setTotalGuest(String totalGuest) {
        this.totalGuest = totalGuest;
    }

    public String getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(String totalRoom) {
        this.totalRoom = totalRoom;
    }

    public String getUniqid() {
        return uniqid;
    }

    public void setUniqid(String uniqid) {
        this.uniqid = uniqid;
    }

    public String getUserCheckin() {
        return userCheckin;
    }

    public void setUserCheckin(String userCheckin) {
        this.userCheckin = userCheckin;
    }

    public String getUserCheckout() {
        return userCheckout;
    }

    public void setUserCheckout(String userCheckout) {
        this.userCheckout = userCheckout;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
