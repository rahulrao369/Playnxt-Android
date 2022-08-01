
package com.cw.playnxt.model.HomeSearch;

import com.google.gson.annotations.Expose;

public class SearchParaRes {

    @Expose
    private String key;
    @Expose
    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
