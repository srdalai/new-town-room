
package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SingleHotelID {

    @SerializedName("hotel_id")
    private String hotelId;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

}
