package com.cw.playnxt.model.StaticModel;

public class GameFragmentModel {
    int icon;
    String heading;
    String number;
    String plan_type;

    public GameFragmentModel(int icon, String heading, String number,String plan_type) {
        this.icon = icon;
        this.heading = heading;
        this.number = number;
        this.plan_type = plan_type;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
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
