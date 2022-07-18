
package com.cw.playnxt.model.PurchasePlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchasePlanParaRes {

    @SerializedName("card_number")
    private String cardNumber;
    @Expose
    private String cvv;
    @Expose
    private Long month;
    @Expose
    private String name;
    @SerializedName("plan_id")
    private Long planId;
    @Expose
    private String year;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Long getMonth() {
        return month;
    }

    public void setMonth(Long month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
