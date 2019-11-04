package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;
import com.newtownroom.userapp.models.BookingData;
import com.newtownroom.userapp.models.BookingPrice;
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.models.LocalInterest;
import com.newtownroom.userapp.models.RulesData;
import com.newtownroom.userapp.models.UserData;

import java.util.ArrayList;

public class BookingDetailsResponses {

    @SerializedName("code")
    private int code;

    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("booking")
    private ArrayList<BookingData> booking;

    @SerializedName("user")
    private ArrayList<UserData> user;

    @SerializedName("hotel")
    private HotelData hotel;

    @SerializedName("price")
    private BookingPrice bookingPrice;

    @SerializedName("hotel_rule_label")
    private String hotel_rule_label;

    @SerializedName("hotel_rules")
    private ArrayList<RulesData> hotel_rules;

    @SerializedName("interests_label")
    private String interests_label;

    @SerializedName("interests")
    private ArrayList<LocalInterest> interests;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<BookingData> getBooking() {
        return booking;
    }

    public void setBooking(ArrayList<BookingData> booking) {
        this.booking = booking;
    }

    public ArrayList<UserData> getUser() {
        return user;
    }

    public void setUser(ArrayList<UserData> user) {
        this.user = user;
    }

    public HotelData getHotel() {
        return hotel;
    }

    public void setHotel(HotelData hotel) {
        this.hotel = hotel;
    }

    public BookingPrice getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(BookingPrice bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public String getHotel_rule_label() {
        return hotel_rule_label;
    }

    public void setHotel_rule_label(String hotel_rule_label) {
        this.hotel_rule_label = hotel_rule_label;
    }

    public ArrayList<RulesData> getHotel_rules() {
        return hotel_rules;
    }

    public void setHotel_rules(ArrayList<RulesData> hotel_rules) {
        this.hotel_rules = hotel_rules;
    }

    public String getInterests_label() {
        return interests_label;
    }

    public void setInterests_label(String interests_label) {
        this.interests_label = interests_label;
    }

    public ArrayList<LocalInterest> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<LocalInterest> interests) {
        this.interests = interests;
    }
}
