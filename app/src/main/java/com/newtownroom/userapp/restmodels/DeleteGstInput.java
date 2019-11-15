
package com.newtownroom.userapp.restmodels;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DeleteGstInput {

    @SerializedName("gst_id")
    private String gstId;
    @SerializedName("user_id")
    private int userId;

    public String getGstId() {
        return gstId;
    }

    public void setGstId(String gstId) {
        this.gstId = gstId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
