
package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SingleBookingID {

    @SerializedName("booking_id")
    private int bookingId;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

}
