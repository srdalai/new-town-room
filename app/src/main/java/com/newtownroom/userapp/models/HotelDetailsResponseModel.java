package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HotelDetailsResponseModel {

    @SerializedName("hotel")
    private ArrayList<HotelData> hotelDataList;

    @SerializedName("price")
    private ArrayList<PriceData> priceDataList;

    //@SerializedName("images")
    private ArrayList<String> imageList;

    @SerializedName("amenities")
    private ArrayList<AmenitiesData> amenitiesList;

    @SerializedName("extra_services")
    private ArrayList<ServiceData> extraServicesList;

    public HotelDetailsResponseModel() {
    }

    public ArrayList<HotelData> getHotelDataList() {
        return hotelDataList;
    }

    public void setHotelDataList(ArrayList<HotelData> hotelDataList) {
        this.hotelDataList = hotelDataList;
    }

    public ArrayList<PriceData> getPriceDataList() {
        return priceDataList;
    }

    public void setPriceDataList(ArrayList<PriceData> priceDataList) {
        this.priceDataList = priceDataList;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public ArrayList<AmenitiesData> getAmenitiesList() {
        return amenitiesList;
    }

    public void setAmenitiesList(ArrayList<AmenitiesData> amenitiesList) {
        this.amenitiesList = amenitiesList;
    }

    public ArrayList<ServiceData> getExtraServicesList() {
        return extraServicesList;
    }

    public void setExtraServicesList(ArrayList<ServiceData> extraServicesList) {
        this.extraServicesList = extraServicesList;
    }
}
