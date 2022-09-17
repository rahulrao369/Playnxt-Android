
package com.cw.playnxt.model.GetPlatformGenre;

import com.google.gson.annotations.Expose;

public class GetPlatformGenreResponse {

    @Expose
    private GetPlatformGenredata data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetPlatformGenredata getData() {
        return data;
    }

    public void setData(GetPlatformGenredata data) {
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
