
package com.cw.playnxt.model.MyActivePlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivePlan {

    @Expose
    private String amount;
    @Expose
    private String description;

    @SerializedName("end_date")
    private String endDate;

    @SerializedName("start_date")
    private String startDate;

    @Expose
    private String title;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
