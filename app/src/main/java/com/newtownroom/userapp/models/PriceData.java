package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

public class PriceData {

    @SerializedName("id")
    private String id;

    @SerializedName("hotel_id")
    private String hotelID;

    @SerializedName("guest")
    private String guest;

    @SerializedName("price")
    private String price;

    @SerializedName("discount_type")
    private String discountType;

    @SerializedName("amount")
    private String amount;

    @SerializedName("selling_price")
    private String sellingPrice;

    public PriceData() {
    }

    public PriceData(String id, String hotelID, String guest, String price, String discountType, String amount, String sellingPrice) {
        this.id = id;
        this.hotelID = hotelID;
        this.guest = guest;
        this.price = price;
        this.discountType = discountType;
        this.amount = amount;
        this.sellingPrice = sellingPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
