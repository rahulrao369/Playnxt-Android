
package com.cw.playnxt.model.LikeAndUnlikeProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeAndUnlikeProfileParaRes {

    @Expose
    private String status;
    @SerializedName("user_id")
    private Long userId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
