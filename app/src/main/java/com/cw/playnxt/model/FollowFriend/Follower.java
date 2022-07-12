
package com.cw.playnxt.model.FollowFriend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Follower {

    @Expose
    private String createdAt;
    @SerializedName("follower_id")
    private String followerId;
    @SerializedName("follw_date")
    private String follwDate;
    @Expose
    private Long id;
    @Expose
    private String updatedAt;
    @SerializedName("user_id")
    private Long userId;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getFollwDate() {
        return follwDate;
    }

    public void setFollwDate(String follwDate) {
        this.follwDate = follwDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
