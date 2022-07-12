
package com.cw.playnxt.model.HomeButton;

import com.google.gson.annotations.Expose;


public class HomeButtonResponse {

    @Expose
    private Data data;
    @Expose
    private String message;
    @Expose
    private Long presence;
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

    public Long getPresence() {
        return presence;
    }

    public void setPresence(Long presence) {
        this.presence = presence;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
