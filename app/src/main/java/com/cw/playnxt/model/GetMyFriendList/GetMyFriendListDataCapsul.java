package com.cw.playnxt.model.GetMyFriendList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMyFriendListDataCapsul {

    @Expose
    private int like;

    @SerializedName("total_like")
    private Long totalLike;

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("user_img")
    private String userImg;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("title")
    private String title;

    @SerializedName("listName")
    private String listName;

    @SerializedName("addedTime")
    private String addedTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public Long getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Long totalLike) {
        this.totalLike = totalLike;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
