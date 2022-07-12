
package com.cw.playnxt.model.GetMyFriendProfile;

import com.google.gson.annotations.Expose;

public class GetMyFriendProfileResponse {

    @Expose
    private GetMyFriendProfileData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetMyFriendProfileData getData() {
        return data;
    }

    public void setData(GetMyFriendProfileData data) {
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
