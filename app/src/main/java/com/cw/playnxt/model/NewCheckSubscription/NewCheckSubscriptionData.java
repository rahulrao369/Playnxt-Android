
package com.cw.playnxt.model.NewCheckSubscription;

import com.google.gson.annotations.Expose;

public class NewCheckSubscriptionData {
    @Expose
    private NewCheckSubscription subscription;

    public NewCheckSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(NewCheckSubscription subscription) {
        this.subscription = subscription;
    }
}
