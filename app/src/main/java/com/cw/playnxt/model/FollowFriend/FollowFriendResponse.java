
package com.cw.playnxt.model.FollowFriend;

import com.google.gson.annotations.Expose;

public class FollowFriendResponse {

    @Expose
    private FollowFriendData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public FollowFriendData getData() {
        return data;
    }

    public void setData(FollowFriendData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
