package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

public class AmenitiesData {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String iconImage;

    private int iconDrawable;
    private boolean isAvailable;

    public AmenitiesData(int iconDrawable, String name, boolean isAvailable) {
        this.iconDrawable = iconDrawable;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public int getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(int iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
