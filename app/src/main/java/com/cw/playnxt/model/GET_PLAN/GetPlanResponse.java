
package com.cw.playnxt.model.GET_PLAN;

import com.google.gson.annotations.Expose;

public class GetPlanResponse {

    @Expose
    private GetPlanData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetPlanData getData() {
        return data;
    }

    public void setData(GetPlanData data) {
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
