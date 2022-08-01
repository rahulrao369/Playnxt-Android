
package com.cw.playnxt.model.HomeSearch.UserSearch;

import com.google.gson.annotations.Expose;

public class SearchUserResponse {

    @Expose
    private SearchUserData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public SearchUserData getData() {
        return data;
    }

    public void setData(SearchUserData data) {
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
