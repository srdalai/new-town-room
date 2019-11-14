package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

public class SingleUserID {

    @SerializedName("user_id")
    private int userId;

    public SingleUserID(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
