
package com.cw.playnxt.model.StatsData;

import com.google.gson.annotations.Expose;

public class GetStatsResponse {

    @Expose
    private Data data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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
