package com.cw.playnxt.model.SubscriptionPlan;

import com.google.gson.annotations.SerializedName;

public class GetPaymentSummaryREq {

    @SerializedName("plan_id")
    private int planId;
    @SerializedName("code")
    private String Code;


    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
