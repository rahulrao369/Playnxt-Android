
package com.cw.playnxt.model.UnfollowFriend;

import com.google.gson.annotations.SerializedName;

public class UnfollowFriendParaRes {

    @SerializedName("user_id")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
