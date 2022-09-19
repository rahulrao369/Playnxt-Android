package com.cw.playnxt.model.StatsData;

import com.google.gson.annotations.Expose;

public class staticStatDataModel {
    @Expose
    private Double avgrate;
    @Expose
    private Long backlogcount;
    @Expose
    private Long completedcount;
    @Expose
    private Long currentplayingcount;
    @Expose
    private Long ontheshelfcount;
    @Expose
    private Long ratingtotal;
    @Expose
    private Long rolledcreditcount;
    @Expose
    private Long takingbreakcount;
    @Expose
    private Long totalgames;
    @Expose
    private Long wishlistcount;

    public staticStatDataModel(Long totalgames, Long backlogcount, Long wishlistcount, Long ontheshelfcount, Long rolledcreditcount, Long completedcount, Long currentplayingcount, Long takingbreakcount, Long ratingtotal, Double avgrate) {
        this.avgrate = avgrate;
        this.backlogcount = backlogcount;
        this.completedcount = completedcount;
        this.currentplayingcount = currentplayingcount;
        this.ontheshelfcount = ontheshelfcount;
        this.ratingtotal = ratingtotal;
        this.rolledcreditcount = rolledcreditcount;
        this.takingbreakcount = takingbreakcount;
        this.totalgames = totalgames;
        this.wishlistcount = wishlistcount;
    }

    public Double getAvgrate() {
        return avgrate;
    }

    public void setAvgrate(Double avgrate) {
        this.avgrate = avgrate;
    }

    public Long getBacklogcount() {
        return backlogcount;
    }

    public void setBacklogcount(Long backlogcount) {
        this.backlogcount = backlogcount;
    }

    public Long getCompletedcount() {
        return completedcount;
    }

    public void setCompletedcount(Long completedcount) {
        this.completedcount = completedcount;
    }

    public Long getCurrentplayingcount() {
        return currentplayingcount;
    }

    public void setCurrentplayingcount(Long currentplayingcount) {
        this.currentplayingcount = currentplayingcount;
    }

    public Long getOntheshelfcount() {
        return ontheshelfcount;
    }

    public void setOntheshelfcount(Long ontheshelfcount) {
        this.ontheshelfcount = ontheshelfcount;
    }

    public Long getRatingtotal() {
        return ratingtotal;
    }

    public void setRatingtotal(Long ratingtotal) {
        this.ratingtotal = ratingtotal;
    }

    public Long getRolledcreditcount() {
        return rolledcreditcount;
    }

    public void setRolledcreditcount(Long rolledcreditcount) {
        this.rolledcreditcount = rolledcreditcount;
    }

    public Long getTakingbreakcount() {
        return takingbreakcount;
    }

    public void setTakingbreakcount(Long takingbreakcount) {
        this.takingbreakcount = takingbreakcount;
    }

    public Long getTotalgames() {
        return totalgames;
    }

    public void setTotalgames(Long totalgames) {
        this.totalgames = totalgames;
    }

    public Long getWishlistcount() {
        return wishlistcount;
    }

    public void setWishlistcount(Long wishlistcount) {
        this.wishlistcount = wishlistcount;
    }
}
