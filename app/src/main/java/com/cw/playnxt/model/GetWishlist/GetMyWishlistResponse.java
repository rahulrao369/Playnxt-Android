
package com.cw.playnxt.model.GetWishlist;

import com.google.gson.annotations.Expose;

public class GetMyWishlistResponse {

    @Expose
    private GetMyWishlistData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetMyWishlistData getData() {
        return data;
    }

    public void setData(GetMyWishlistData data) {
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
