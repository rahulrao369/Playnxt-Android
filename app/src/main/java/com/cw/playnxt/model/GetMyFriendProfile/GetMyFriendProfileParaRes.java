
package com.cw.playnxt.model.GetMyFriendProfile;

import com.google.gson.annotations.SerializedName;

public class GetMyFriendProfileParaRes {

    @SerializedName("user_id")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
