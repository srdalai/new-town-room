
package com.newtownroom.userapp.models;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Interest {

    @Expose
    private String image;
    @Expose
    private String name;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
