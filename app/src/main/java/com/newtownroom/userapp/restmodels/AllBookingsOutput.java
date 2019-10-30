package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;
import com.newtownroom.userapp.models.BookingData;

import java.util.ArrayList;

public class AllBookingsOutput {

    @SerializedName("upcoming")
    private ArrayList<BookingData> upcomingBookings;

    @SerializedName("completed")
    private ArrayList<BookingData> completedBookings;

    @SerializedName("canceled")
    private ArrayList<BookingData> canceledBookings;

    public AllBookingsOutput() {
    }

    public AllBookingsOutput(ArrayList<BookingData> upcomingBookings, ArrayList<BookingData> completedBookings, ArrayList<BookingData> canceledBookings) {
        this.upcomingBookings = upcomingBookings;
        this.completedBookings = completedBookings;
        this.canceledBookings = canceledBookings;
    }

    public ArrayList<BookingData> getUpcomingBookings() {
        return upcomingBookings;
    }

    public void setUpcomingBookings(ArrayList<BookingData> upcomingBookings) {
        this.upcomingBookings = upcomingBookings;
    }

    public ArrayList<BookingData> getCompletedBookings() {
        return completedBookings;
    }

    public void setCompletedBookings(ArrayList<BookingData> completedBookings) {
        this.completedBookings = completedBookings;
    }

    public ArrayList<BookingData> getCanceledBookings() {
        return canceledBookings;
    }

    public void setCanceledBookings(ArrayList<BookingData> canceledBookings) {
        this.canceledBookings = canceledBookings;
    }
}
