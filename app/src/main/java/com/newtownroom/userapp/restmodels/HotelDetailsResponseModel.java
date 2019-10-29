package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;
import com.newtownroom.userapp.models.AmenitiesData;
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.models.ImageModel;
import com.newtownroom.userapp.models.PriceData;
import com.newtownroom.userapp.models.RulesData;
import com.newtownroom.userapp.models.ServiceData;

import java.util.ArrayList;

public class HotelDetailsResponseModel {

    @SerializedName("hotel")
    private ArrayList<HotelData> hotelDataList;

    @SerializedName("price")
    private ArrayList<PriceData> priceDataList;

    @SerializedName("images")
    private ArrayList<ImageModel> imageList;

    @SerializedName("amenities")
    private ArrayList<AmenitiesData> amenitiesList;

    @SerializedName("extra_services")
    private ArrayList<ServiceData> extraServicesList;

    @SerializedName("hotel_rules")
    private ArrayList<RulesData> rulesDataList;

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

    public ArrayList<ImageModel> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<ImageModel> imageList) {
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

    public ArrayList<RulesData> getRulesDataList() {
        return rulesDataList;
    }

    public void setRulesDataList(ArrayList<RulesData> rulesDataList) {
        this.rulesDataList = rulesDataList;
    }
}
