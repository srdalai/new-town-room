package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;
import com.newtownroom.userapp.models.BannerData;
import com.newtownroom.userapp.models.FeaturedHotel;

import java.util.ArrayList;

public class HomeResponse {

    @SerializedName("banners")
    private ArrayList<BannerData> banners;

    @SerializedName("hotels")
    private ArrayList<FeaturedHotel> featuredHotels;

    public ArrayList<BannerData> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<BannerData> banners) {
        this.banners = banners;
    }

    public ArrayList<FeaturedHotel> getFeaturedHotels() {
        return featuredHotels;
    }

    public void setFeaturedHotels(ArrayList<FeaturedHotel> featuredHotels) {
        this.featuredHotels = featuredHotels;
    }
}
