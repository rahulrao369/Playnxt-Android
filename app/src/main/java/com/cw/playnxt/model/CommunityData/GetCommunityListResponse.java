
package com.cw.playnxt.model.CommunityData;

import com.google.gson.annotations.Expose;


public class GetCommunityListResponse {

    @Expose
    private GetCommunityListData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetCommunityListData getData() {
        return data;
    }

    public void setData(GetCommunityListData data) {
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
