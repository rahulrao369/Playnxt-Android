
package com.cw.playnxt.model.GetGameNote;

import com.google.gson.annotations.Expose;

public class GetGameNoteResponse {

    @Expose
    private GetGameNoteData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetGameNoteData getData() {
        return data;
    }

    public void setData(GetGameNoteData data) {
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
