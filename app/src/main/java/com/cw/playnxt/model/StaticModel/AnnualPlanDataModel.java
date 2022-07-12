package com.cw.playnxt.model.StaticModel;

public class AnnualPlanDataModel {
    String freatures;
    int right;
    int cross;

    public AnnualPlanDataModel(String freatures, int right, int cross) {
        this.freatures = freatures;
        this.right = right;
        this.cross = cross;
    }

    public String getFreatures() {
        return freatures;
    }

    public void setFreatures(String freatures) {
        this.freatures = freatures;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getCross() {
        return cross;
    }

    public void setCross(int cross) {
        this.cross = cross;
    }
}
