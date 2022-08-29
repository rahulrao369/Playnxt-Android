
package com.cw.playnxt.model.CheckSubscriptionFinal;

import com.google.gson.annotations.Expose;

public class CheckSubscriptionFinalData {

    @Expose
    private int free_backlog;

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

    public int getFree_backlog() {
        return free_backlog;
    }

    public void setFree_backlog(int free_backlog) {
        this.free_backlog = free_backlog;
    }
}
