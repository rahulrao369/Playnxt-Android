package com.cw.playnxt.model.GetMyProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Follower {
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @SerializedName("is_followed")
    @Expose
    private Integer is_follow;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("mutual_friend")
    @Expose
    private int mutual_friend;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(Integer is_follow) {
        this.is_follow = is_follow;
    }

    public int getMutual_friend() {
        return mutual_friend;
    }

    public void setMutual_friend(int mutual_friend) {
        this.mutual_friend = mutual_friend;
    }

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
}
