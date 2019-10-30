
package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AllBookingsInput {

    @SerializedName("user_id")
    private int userId;

    @SerializedName("uniqid")
    private String uniqid;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUniqid() {
        return uniqid;
    }

    public void setUniqid(String uniqid) {
        this.uniqid = uniqid;
    }
}
