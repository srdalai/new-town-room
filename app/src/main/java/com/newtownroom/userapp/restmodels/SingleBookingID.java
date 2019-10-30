
package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SingleBookingID {

    @SerializedName("booking_id")
    private Long bookingId;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        bookingId = bookingId;
    }

}
