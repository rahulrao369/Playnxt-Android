
package com.cw.playnxt.model.SubscriptionPlan;

import java.util.List;
import com.google.gson.annotations.Expose;

public class SubscriptionPlanData {

    @Expose
    private List<Plan> plan;

    public List<Plan> getPlan() {
        return plan;
    }

    public void setPlan(List<Plan> plan) {
        this.plan = plan;
    }

}
