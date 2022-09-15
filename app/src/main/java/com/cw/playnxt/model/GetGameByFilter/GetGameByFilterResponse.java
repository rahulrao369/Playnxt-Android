
package com.cw.playnxt.model.GetGameByFilter;

import com.google.gson.annotations.Expose;

public class GetGameByFilterResponse {

    @Expose
    private GetGameByFilterData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetGameByFilterData getData() {
        return data;
    }

    public void setData(GetGameByFilterData data) {
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
