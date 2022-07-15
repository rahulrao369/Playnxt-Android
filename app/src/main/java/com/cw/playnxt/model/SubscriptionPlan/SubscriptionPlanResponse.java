
package com.cw.playnxt.model.SubscriptionPlan;

import com.google.gson.annotations.Expose;

public class SubscriptionPlanResponse {

    @Expose
    private SubscriptionPlanData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public SubscriptionPlanData getData() {
        return data;
    }

    public void setData(SubscriptionPlanData data) {
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
