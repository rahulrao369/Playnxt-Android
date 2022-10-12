
package com.cw.playnxt.model.GetMyFriendProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyFriendProfileGame {

    @Expose
    private String description;
    @SerializedName("game_id")
    private Long gameId;
    @Expose
    private String genre;
    @Expose
    private String image;
    @Expose
    private String platform;
    @Expose
    private String title;

    @Expose
    private String image_type;

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
