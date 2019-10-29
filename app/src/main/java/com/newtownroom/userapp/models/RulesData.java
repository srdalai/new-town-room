package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

public class RulesData {

    @SerializedName("id")
    private String id;

    @SerializedName("hotel_id")
    private String hotelID;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    public RulesData(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
