
package com.cw.playnxt.model.HomeSearch;

import com.google.gson.annotations.Expose;


public class SearchResponse {

    @Expose
    private SearchData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public SearchData getData() {
        return data;
    }

    public void setData(SearchData data) {
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
