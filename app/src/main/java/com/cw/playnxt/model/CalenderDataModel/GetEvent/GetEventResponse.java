
package com.cw.playnxt.model.CalenderDataModel.GetEvent;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class GetEventResponse implements Serializable {

    @Expose
    private GetEventData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetEventData getData() {
        return data;
    }

    public void setData(GetEventData data) {
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
