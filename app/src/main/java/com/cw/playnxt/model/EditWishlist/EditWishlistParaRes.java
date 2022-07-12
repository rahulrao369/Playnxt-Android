
package com.cw.playnxt.model.EditWishlist;

import com.google.gson.annotations.SerializedName;

public class EditWishlistParaRes {

    @SerializedName("wishlist_id")
    private Long wishlistId;
    @SerializedName("wishlist_name")
    private String wishlistName;

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

}
