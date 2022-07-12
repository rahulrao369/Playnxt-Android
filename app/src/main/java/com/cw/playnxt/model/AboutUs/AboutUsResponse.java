
package com.cw.playnxt.model.AboutUs;

import com.google.gson.annotations.Expose;

public class AboutUsResponse {

    @Expose
    private AboutUsData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public AboutUsData getData() {
        return data;
    }

    public void setData(AboutUsData data) {
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
