package com.cw.playnxt.model.StaticModel;

public class GameFragmentModel {
    int icon;
    String heading;
    String number;
    String subscribed;

    public GameFragmentModel(int icon, String heading, String number,String subscribed) {
        this.icon = icon;
        this.heading = heading;
        this.number = number;
        this.subscribed = subscribed;
    }

    public String getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(String subscribed) {
        this.subscribed = subscribed;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
