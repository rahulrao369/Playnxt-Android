
package com.cw.playnxt.model.MyActivePlan;

import com.google.gson.annotations.SerializedName;

public class MyActivePlanData {

    @SerializedName("active_plan")
    private ActivePlan activePlan;

    public ActivePlan getActivePlan() {
        return activePlan;
    }

    public void setActivePlan(ActivePlan activePlan) {
        this.activePlan = activePlan;
    }

}
