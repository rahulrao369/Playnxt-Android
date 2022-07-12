
package com.cw.playnxt.model.HomeData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Following {

    @Expose
    private String image;
    @Expose
    private String name;
    @SerializedName("user_id")
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
