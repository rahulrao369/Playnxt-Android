
package com.cw.playnxt.model.NewCheckSubscription;

import com.google.gson.annotations.Expose;

public class NewCheckSubscriptionResponse {

    @Expose
    private NewCheckSubscriptionData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public NewCheckSubscriptionData getData() {
        return data;
    }

    public void setData(NewCheckSubscriptionData data) {
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
