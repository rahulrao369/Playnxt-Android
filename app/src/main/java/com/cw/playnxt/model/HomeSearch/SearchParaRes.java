
package com.cw.playnxt.model.HomeSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchParaRes {

    @SerializedName("filter_bygame")
    private String filterBygame;
    @SerializedName("filter_byuser")
    private String filterByuser;
    @Expose
    private String key;

    public String getFilterBygame() {
        return filterBygame;
    }

    public void setFilterBygame(String filterBygame) {
        this.filterBygame = filterBygame;
    }

    public String getFilterByuser() {
        return filterByuser;
    }

    public void setFilterByuser(String filterByuser) {
        this.filterByuser = filterByuser;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
