
package com.cw.playnxt.model.CommunityData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CapsulCommunityData {

    @Expose
    private String image;

    @Expose
    private int like;

    @Expose
    private String name;

    @SerializedName("total_like")
    private Long totalLike;

    @SerializedName("user_id")
    private Long userId;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
