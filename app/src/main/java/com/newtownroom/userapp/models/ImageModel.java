package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

public class ImageModel {

    @SerializedName("image")
    private String imageUrl;

    public ImageModel() {
    }

    public ImageModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
