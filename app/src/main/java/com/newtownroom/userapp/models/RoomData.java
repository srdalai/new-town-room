package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoomData {

    private int viewID;

    @SerializedName("adult")
    private int adults = 1;

    @SerializedName("child")
    private int children = 0;

    public RoomData() {
    }

    public RoomData(int adults, int children) {
        this.adults = adults;
        this.children = children;
    }

    public int getViewID() {
        return viewID;
    }

    public void setViewID(int viewID) {
        this.viewID = viewID;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }
}
