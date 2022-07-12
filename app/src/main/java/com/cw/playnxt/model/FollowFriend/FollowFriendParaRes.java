
package com.cw.playnxt.model.FollowFriend;

import com.google.gson.annotations.SerializedName;

public class FollowFriendParaRes {

    @SerializedName("user_id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
