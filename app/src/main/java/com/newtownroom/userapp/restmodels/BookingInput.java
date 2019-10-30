package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;
import com.newtownroom.userapp.models.RoomData;

import java.util.ArrayList;

public class BookingInput {

    @SerializedName("checkin")
    private String checkInDate;

    @SerializedName("checkout")
    private String CheckOutDate;

    @SerializedName("hotel_id")
    private String hotelID;

    @SerializedName("user_id")
    private int userID;

    @SerializedName("uniqid")
    private String uniqueID;

    @SerializedName("total_guest")
    private int totalGuest;

    @SerializedName("rooms")
    private ArrayList<RoomData> rooms;

    @SerializedName("booked_price")
    private double bookedPrice;

    @SerializedName("coupon")
    private String coupon;

    @SerializedName("extra_services")
    private ArrayList<Integer> extraServices;

    public BookingInput() {
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public int getUserID() {
        return userID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTotalGuest() {
        return totalGuest;
    }

    public void setTotalGuest(int totalGuest) {
        this.totalGuest = totalGuest;
    }

    public ArrayList<RoomData> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<RoomData> rooms) {
        this.rooms = rooms;
    }

    public double getBookedPrice() {
        return bookedPrice;
    }

    public void setBookedPrice(double bookedPrice) {
        this.bookedPrice = bookedPrice;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public ArrayList<Integer> getExtraServices() {
        return extraServices;
    }

    public void setExtraServices(ArrayList<Integer> extraServices) {
        this.extraServices = extraServices;
    }
}
