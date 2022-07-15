
package com.cw.playnxt.model.SubscriptionPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatisSubscriptionPlansListWithSymbol {

    @SerializedName("list_name")
    private String listName;
    @Expose
    private Long symbol;

    public StatisSubscriptionPlansListWithSymbol(String listName, Long symbol) {
        this.listName = listName;
        this.symbol = symbol;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Long getSymbol() {
        return symbol;
    }

    public void setSymbol(Long symbol) {
        this.symbol = symbol;
    }

}
