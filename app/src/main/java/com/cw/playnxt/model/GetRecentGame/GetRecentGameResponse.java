
package com.cw.playnxt.model.GetRecentGame;

import com.google.gson.annotations.Expose;

public class GetRecentGameResponse {

    @Expose
    private GetRecentGameData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetRecentGameData getData() {
        return data;
    }

    public void setData(GetRecentGameData data) {
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
