
package com.cw.playnxt.model.CheckSubscriptionFinal;

import com.google.gson.annotations.Expose;

public class CheckSubscriptionFinalResponse {

    @Expose
    private CheckSubscriptionFinalData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public CheckSubscriptionFinalData getData() {
        return data;
    }

    public void setData(CheckSubscriptionFinalData data) {
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
