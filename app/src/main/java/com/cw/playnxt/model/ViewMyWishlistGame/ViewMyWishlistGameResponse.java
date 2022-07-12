
package com.cw.playnxt.model.ViewMyWishlistGame;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ViewMyWishlistGameResponse implements Serializable {

    @Expose
    private ViewMyWishlistGameData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public ViewMyWishlistGameData getData() {
        return data;
    }

    public void setData(ViewMyWishlistGameData data) {
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
