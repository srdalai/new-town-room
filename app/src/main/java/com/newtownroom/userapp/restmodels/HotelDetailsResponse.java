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

    @SerializedName("pre_coupon_code")
    private String pre_coupon_code;

    @SerializedName("pre_coupon_amount")
    private float pre_coupon_amount;

    @SerializedName("price")
    private ArrayList<PriceData> priceDataList;

    @SerializedName("images")
    private ArrayList<ImageModel> imageList;

    @SerializedName("amenities_label")
    private String amenitiesLabel;

    @SerializedName("amenities")
    private ArrayList<AmenitiesData> amenitiesList;

    @SerializedName("extra_services_label")
    private String extraServicesLabel;

    @SerializedName("extra_services")
    private ArrayList<ServiceData> extraServicesList;

    @SerializedName("local_interest_label")
    private String localInterestLabel;

    @SerializedName("local_interest")
    private ArrayList<LocalInterest> localInterests;

    @SerializedName("nearby_label")
    private String nearbyLabel;

    @SerializedName("nearby")
    private ArrayList<NearBy> nearByList;

    @SerializedName("hotel_rules_label")
    private String hotelRulesLabel;

    @SerializedName("hotel_rules")
    private ArrayList<RulesData> rulesDataList;

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

    public String getAmenitiesLabel() {
        return amenitiesLabel;
    }

    public void setAmenitiesLabel(String amenitiesLabel) {
        this.amenitiesLabel = amenitiesLabel;
    }

    public ArrayList<AmenitiesData> getAmenitiesList() {
        return amenitiesList;
    }

    public void setAmenitiesList(ArrayList<AmenitiesData> amenitiesList) {
        this.amenitiesList = amenitiesList;
    }

    public String getExtraServicesLabel() {
        return extraServicesLabel;
    }

    public void setExtraServicesLabel(String extraServicesLabel) {
        this.extraServicesLabel = extraServicesLabel;
    }

    public ArrayList<ServiceData> getExtraServicesList() {
        return extraServicesList;
    }

    public void setExtraServicesList(ArrayList<ServiceData> extraServicesList) {
        this.extraServicesList = extraServicesList;
    }

    public String getLocalInterestLabel() {
        return localInterestLabel;
    }

    public void setLocalInterestLabel(String localInterestLabel) {
        this.localInterestLabel = localInterestLabel;
    }

    public ArrayList<LocalInterest> getLocalInterests() {
        return localInterests;
    }

    public void setLocalInterests(ArrayList<LocalInterest> localInterests) {
        this.localInterests = localInterests;
    }

    public String getNearbyLabel() {
        return nearbyLabel;
    }

    public void setNearbyLabel(String nearbyLabel) {
        this.nearbyLabel = nearbyLabel;
    }

    public ArrayList<NearBy> getNearByList() {
        return nearByList;
    }

    public void setNearByList(ArrayList<NearBy> nearByList) {
        this.nearByList = nearByList;
    }

    public String getHotelRulesLabel() {
        return hotelRulesLabel;
    }

    public void setHotelRulesLabel(String hotelRulesLabel) {
        this.hotelRulesLabel = hotelRulesLabel;
    }

    public ArrayList<RulesData> getRulesDataList() {
        return rulesDataList;
    }

    public void setRulesDataList(ArrayList<RulesData> rulesDataList) {
        this.rulesDataList = rulesDataList;
    }
}
