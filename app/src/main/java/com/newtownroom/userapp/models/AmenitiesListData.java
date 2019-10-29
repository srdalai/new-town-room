package com.newtownroom.userapp.models;

import java.util.ArrayList;

public class AmenitiesListData {

    private ArrayList<AmenitiesData> amenitiesData;

    public AmenitiesListData(ArrayList<AmenitiesData> amenitiesData) {
        this.amenitiesData = amenitiesData;
    }

    public ArrayList<AmenitiesData> getAmenitiesData() {
        return amenitiesData;
    }

    public void setAmenitiesData(ArrayList<AmenitiesData> amenitiesData) {
        this.amenitiesData = amenitiesData;
    }
}
