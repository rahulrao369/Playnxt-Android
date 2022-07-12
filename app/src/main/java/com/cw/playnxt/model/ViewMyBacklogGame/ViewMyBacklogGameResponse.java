
package com.cw.playnxt.model.ViewMyBacklogGame;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ViewMyBacklogGameResponse implements Serializable {

    @Expose
    private ViewMyBacklogGameData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public ViewMyBacklogGameData getData() {
        return data;
    }

    public void setData(ViewMyBacklogGameData data) {
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
