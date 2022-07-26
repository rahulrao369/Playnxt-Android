
package com.cw.playnxt.model.PurchaseFreePlan;

import com.google.gson.annotations.SerializedName;

public class PurchaseFreePlanParaRes {

    @SerializedName("plan_id")
    private Long planId;

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

}
