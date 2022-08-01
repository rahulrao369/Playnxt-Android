
package com.cw.playnxt.model.HomeSearch.GameSearch;

import com.google.gson.annotations.Expose;

public class SearchGameResponse {

    @Expose
    private SearchGameData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public SearchGameData getData() {
        return data;
    }

    public void setData(SearchGameData data) {
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
