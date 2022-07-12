
package com.cw.playnxt.model.GetCategoryListName;

import java.util.List;
import com.google.gson.annotations.Expose;


public class GetCategoryWishListNameData {

    @Expose
    private List<Wishlist> wishlist;

    public List<Wishlist> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Wishlist> wishlist) {
        this.wishlist = wishlist;
    }

}
