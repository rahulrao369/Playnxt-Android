
package com.cw.playnxt.model.NewCheckSubscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCheckSubscription {

    @SerializedName("access_new_features")
    private Long accessNewFeatures;
    @SerializedName("ad_free")
    private Long adFree;
    @Expose
    private Long amount;
    @Expose
    private Long backlog;
    @Expose
    private Long calender;
    @SerializedName("comm_activity")
    private Long commActivity;
    @Expose
    private String createdAt;
    @Expose
    private String description;
    @Expose
    private String duration;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("follow_friends")
    private Long followFriends;
    @Expose
    private Long id;
    @SerializedName("message_fnd")
    private Long messageFnd;
    @SerializedName("personal_stats")
    private Long personalStats;
    @SerializedName("plan_id")
    private Long planId;
    @SerializedName("scanning_tool")
    private Long scanningTool;
    @SerializedName("start_date")
    private String startDate;
    @Expose
    private String status;
    @Expose
    private String title;
    @SerializedName("total_backlog")
    private int totalBacklog;
    @Expose
    private String type;
    @Expose
    private String updatedAt;
    @SerializedName("user_id")
    private Long userId;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getScanningTool() {
        return scanningTool;
    }

    public void setScanningTool(Long scanningTool) {
        this.scanningTool = scanningTool;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalBacklog() {
        return totalBacklog;
    }

    public void setTotalBacklog(int totalBacklog) {
        this.totalBacklog = totalBacklog;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
