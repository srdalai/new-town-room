package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

public class HotelDetailsInput {

    @SerializedName("hotel_id")
    private String hotelID;

    public HotelDetailsInput() {
    }

    public HotelDetailsInput(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }
}
