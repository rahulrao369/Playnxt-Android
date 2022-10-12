package com.cw.playnxt.model.CheckSubscriptionFinal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscription {

    @Expose
    private Long amount;

    @Expose
    private String createdAt;

    @SerializedName("end_date")
    private String endDate;

    @Expose
    private Long id;

    @SerializedName("plan_id")
    private Long planId;

    @SerializedName("start_date")
    private String startDate;

    @Expose
    private String status;

    @Expose
    private String type;

    @Expose
    private String updatedAt;

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("grace_taken")
    private int grace_taken;

    @SerializedName("recurring")
    private int recurring;

    @SerializedName("recurring_payment")
    private String recurring_payment;

    public int getGrace_taken() {
        return grace_taken;
    }

    public void setGrace_taken(int grace_taken) {
        this.grace_taken = grace_taken;
    }

    public int getRecurring() {
        return recurring;
    }

    public void setRecurring(int recurring) {
        this.recurring = recurring;
    }

    public String getRecurring_payment() {
        return recurring_payment;
    }

    public void setRecurring_payment(String recurring_payment) {
        this.recurring_payment = recurring_payment;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
