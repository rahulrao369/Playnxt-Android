
package com.cw.playnxt.model.GetGameByFilter;

import java.util.List;
import com.google.gson.annotations.Expose;

public class GetGameByFilterData {

    @Expose
    private List<Newdatum> newdata;

    public List<Newdatum> getNewdata() {
        return newdata;
    }

    public void setNewdata(List<Newdatum> newdata) {
        this.newdata = newdata;
    }

}
