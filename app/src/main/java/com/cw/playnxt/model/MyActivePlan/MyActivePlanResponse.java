
package com.cw.playnxt.model.MyActivePlan;

import com.google.gson.annotations.Expose;

public class MyActivePlanResponse {

    @Expose
    private MyActivePlanData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public MyActivePlanData getData() {
        return data;
    }

    public void setData(MyActivePlanData data) {
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
