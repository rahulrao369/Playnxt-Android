
package com.cw.playnxt.model.PurchasePlan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plan {

    @SerializedName("access_new_features")
    private Long accessNewFeatures;
    @SerializedName("ad_free")
    private Long adFree;
    @Expose
    private Long backlog;
    @Expose
    private Long calender;
    @SerializedName("comm_activity")
    private Long commActivity;
    @Expose
    private String createdAt;
    @Expose
    private String duration;
    @SerializedName("follow_friends")
    private Long followFriends;
    @Expose
    private Long id;
    @SerializedName("message_fnd")
    private Long messageFnd;
    @SerializedName("personal_stats")
    private Long personalStats;
    @SerializedName("scanning_tool")
    private Long scanningTool;
    @Expose
    private String title;
    @Expose
    private String type;
    @Expose
    private String updatedAt;
    @Expose
    private Long viewFriendBW;
    @Expose
    private Long wishlist;

    public Long getAccessNewFeatures() {
        return accessNewFeatures;
    }

    public void setAccessNewFeatures(Long accessNewFeatures) {
        this.accessNewFeatures = accessNewFeatures;
    }

    public Long getAdFree() {
        return adFree;
    }

    public void setAdFree(Long adFree) {
        this.adFree = adFree;
    }

    public Long getBacklog() {
        return backlog;
    }

    public void setBacklog(Long backlog) {
        this.backlog = backlog;
    }

    public Long getCalender() {
        return calender;
    }

    public void setCalender(Long calender) {
        this.calender = calender;
    }

    public Long getCommActivity() {
        return commActivity;
    }

    public void setCommActivity(Long commActivity) {
        this.commActivity = commActivity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getFollowFriends() {
        return followFriends;
    }

    public void setFollowFriends(Long followFriends) {
        this.followFriends = followFriends;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageFnd() {
        return messageFnd;
    }

    public void setMessageFnd(Long messageFnd) {
        this.messageFnd = messageFnd;
    }

    public Long getPersonalStats() {
        return personalStats;
    }

    public void setPersonalStats(Long personalStats) {
        this.personalStats = personalStats;
    }

    public Long getScanningTool() {
        return scanningTool;
    }

    public void setScanningTool(Long scanningTool) {
        this.scanningTool = scanningTool;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getViewFriendBW() {
        return viewFriendBW;
    }

    public void setViewFriendBW(Long viewFriendBW) {
        this.viewFriendBW = viewFriendBW;
    }

    public Long getWishlist() {
        return wishlist;
    }

    public void setWishlist(Long wishlist) {
        this.wishlist = wishlist;
    }

}
