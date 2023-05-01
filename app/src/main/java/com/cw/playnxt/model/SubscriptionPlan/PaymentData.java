package com.cw.playnxt.model.SubscriptionPlan;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PaymentData {

    @SerializedName("actualprice")
    @Expose
    private String actualprice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("finalprice")
    @Expose
    private String finalprice;

    public String getActualprice() {
        return actualprice;
    }

    public void setActualprice(String actualprice) {
        this.actualprice = actualprice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(String finalprice) {
        this.finalprice = finalprice;
    }

}
