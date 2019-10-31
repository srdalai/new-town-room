package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;
import com.newtownroom.userapp.models.AmenitiesData;
import com.newtownroom.userapp.models.HotelData;
import com.newtownroom.userapp.models.ImageModel;
import com.newtownroom.userapp.models.LocalInterest;
import com.newtownroom.userapp.models.NearBy;
import com.newtownroom.userapp.models.PriceData;
import com.newtownroom.userapp.models.RulesData;
import com.newtownroom.userapp.models.ServiceData;

import java.util.ArrayList;

public class HotelDetailsResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("status")
    private String status;

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

    @SerializedName("pre_coupon_code")
    private String pre_coupon_code;

    @SerializedName("pre_coupon_amount")
    private float pre_coupon_amount;

    @SerializedName("local_interest")
    private ArrayList<LocalInterest> localInterests;

    @SerializedName("nearby")
    private ArrayList<NearBy> nearByList;

    public HotelDetailsResponse() {
    }

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

    public String getPre_coupon_code() {
        return pre_coupon_code;
    }

    public void setPre_coupon_code(String pre_coupon_code) {
        this.pre_coupon_code = pre_coupon_code;
    }

    public float getPre_coupon_amount() {
        return pre_coupon_amount;
    }

    public void setPre_coupon_amount(float pre_coupon_amount) {
        this.pre_coupon_amount = pre_coupon_amount;
    }

    public ArrayList<LocalInterest> getLocalInterests() {
        return localInterests;
    }

    public void setLocalInterests(ArrayList<LocalInterest> localInterests) {
        this.localInterests = localInterests;
    }

    public ArrayList<NearBy> getNearByList() {
        return nearByList;
    }

    public void setNearByList(ArrayList<NearBy> nearByList) {
        this.nearByList = nearByList;
    }
}
