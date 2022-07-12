package com.cw.playnxt.model.GetMyProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Following {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;

    public int getMutual_friend() {
        return mutual_friend;
    }

    public void setMutual_friend(int mutual_friend) {
        this.mutual_friend = mutual_friend;
    }

    @SerializedName("mutual_friend")
    @Expose
    private int mutual_friend;
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @SerializedName("image")
    @Expose
    private String image;
}
