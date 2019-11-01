package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeaturedHotel {

    @SerializedName("hotelListLabel")
    private String hotelListLabel;

    @SerializedName("offerBanner")
    private String offerBanner;

    @SerializedName("hotelList")
    private ArrayList<HotelData> hotelList;

    public String getHotelListLabel() {
        return hotelListLabel;
    }

    public void setHotelListLabel(String hotelListLabel) {
        this.hotelListLabel = hotelListLabel;
    }

    public String getOfferBanner() {
        return offerBanner;
    }

    public void setOfferBanner(String offerBanner) {
        this.offerBanner = offerBanner;
    }

    public ArrayList<HotelData> getHotelList() {
        return hotelList;
    }

    public void setHotelList(ArrayList<HotelData> hotelList) {
        this.hotelList = hotelList;
    }
}
