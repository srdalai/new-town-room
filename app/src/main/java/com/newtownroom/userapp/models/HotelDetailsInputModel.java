package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

public class HotelDetailsInputModel {

    @SerializedName("hotel_id")
    private String hotelID;

    public HotelDetailsInputModel() {
    }

    public HotelDetailsInputModel(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }
}
