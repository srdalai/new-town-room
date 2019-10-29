package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GuestData {

    @SerializedName("guests")
    private ArrayList<RoomData> guestData;

    public GuestData(ArrayList<RoomData> guestData) {
        this.guestData = guestData;
    }

    public ArrayList<RoomData> getGuestData() {
        return guestData;
    }

    public void setGuestData(ArrayList<RoomData> guestData) {
        this.guestData = guestData;
    }
}
