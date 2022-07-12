
package com.cw.playnxt.model.HomeButton;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @Expose
    private String description;
    @SerializedName("g_image")
    private String gImage;
    @SerializedName("g_title")
    private String gTitle;
    @SerializedName("game_id")
    private Long gameId;
    @SerializedName("game_status")
    private String gameStatus;
    @Expose
    private String genre;
    @Expose
    private String platform;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGImage() {
        return gImage;
    }

    public void setGImage(String gImage) {
        this.gImage = gImage;
    }

    public String getGTitle() {
        return gTitle;
    }

    public void setGTitle(String gTitle) {
        this.gTitle = gTitle;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
