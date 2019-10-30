
package com.newtownroom.userapp.restmodels;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.newtownroom.userapp.models.RoomData;

@SuppressWarnings("unused")
public class CheckAvailInput {

    @Expose
    private String checkin;
    @Expose
    private String checkout;
    @SerializedName("hotel_id")
    private String hotelId;
    @Expose
    private ArrayList<RoomData> rooms;
    @Expose
    private String uniqid;
    @SerializedName("user_id")
    private int userId;

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public ArrayList<RoomData> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<RoomData> rooms) {
        this.rooms = rooms;
    }

    public String getUniqid() {
        return uniqid;
    }

    public void setUniqid(String uniqid) {
        this.uniqid = uniqid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
