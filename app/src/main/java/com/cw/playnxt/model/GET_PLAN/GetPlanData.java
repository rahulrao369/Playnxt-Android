
package com.cw.playnxt.model.GET_PLAN;

import java.util.List;
import com.google.gson.annotations.Expose;

public class GetPlanData {

    @Expose
    private List<GetPlan> plan;

    public List<GetPlan> getPlan() {
        return plan;
    }

    public void setPlan(List<GetPlan> plan) {
        this.plan = plan;
    }

}
