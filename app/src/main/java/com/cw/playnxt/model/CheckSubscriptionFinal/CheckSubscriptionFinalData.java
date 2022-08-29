
package com.cw.playnxt.model.CheckSubscriptionFinal;

import com.google.gson.annotations.Expose;

public class CheckSubscriptionFinalData {

    @Expose
    private String subscribed;
    @Expose
    private Subscription subscription;

    public String getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(String subscribed) {
        this.subscribed = subscribed;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

}
