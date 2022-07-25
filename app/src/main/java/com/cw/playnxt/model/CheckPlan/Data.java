
package com.cw.playnxt.model.CheckPlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("active_plan")
    private String activePlan;
    @Expose
    private String actplan;
    @SerializedName("total_backlog")
    private int totalBacklog;

    public String getActivePlan() {
        return activePlan;
    }

    public void setActivePlan(String activePlan) {
        this.activePlan = activePlan;
    }

    public String getActplan() {
        return actplan;
    }

    public void setActplan(String actplan) {
        this.actplan = actplan;
    }

    public int getTotalBacklog() {
        return totalBacklog;
    }

    public void setTotalBacklog(int totalBacklog) {
        this.totalBacklog = totalBacklog;
    }

}
