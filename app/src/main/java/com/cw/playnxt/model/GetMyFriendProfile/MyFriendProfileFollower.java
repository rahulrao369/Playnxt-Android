package com.cw.playnxt.model.GetMyFriendProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyFriendProfileFollower {

    @Expose
    private String image;

    @SerializedName("mutual_friend")
    private Long mutualFriend;

    @Expose
    private String name;

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("is_follow")
    private int is_follow;

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getMutualFriend() {
        return mutualFriend;
    }

    public void setMutualFriend(Long mutualFriend) {
        this.mutualFriend = mutualFriend;
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
