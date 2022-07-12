
package com.cw.playnxt.model.SuggestionData;

import com.google.gson.annotations.Expose;

public class SuggestionResponse {

    @Expose
    private SuggestionData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public SuggestionData getData() {
        return data;
    }

    public void setData(SuggestionData data) {
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
