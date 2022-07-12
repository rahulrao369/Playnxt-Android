
package com.cw.playnxt.model.GetWishlist;

import java.util.List;
import com.google.gson.annotations.Expose;

public class GetMyWishlistData {

    @Expose
    private List<Count> count;

    public List<Count> getCount() {
        return count;
    }

    public void setCount(List<Count> count) {
        this.count = count;
    }

}
