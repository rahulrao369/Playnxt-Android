
package com.cw.playnxt.model.GetMyFriendList;

import com.google.gson.annotations.Expose;

public class GetMyFriendListResponse {

    @Expose
    private GetMyFriendListData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetMyFriendListData getData() {
        return data;
    }

    public void setData(GetMyFriendListData data) {
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
