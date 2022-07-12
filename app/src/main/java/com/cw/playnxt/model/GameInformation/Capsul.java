package com.cw.playnxt.model.GameInformation;

import com.google.gson.annotations.SerializedName;

public class Capsul {

    @SerializedName("g_image")
    private String gImage;

 @SerializedName("rating")
    private Double rating;

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @SerializedName("g_title")
    private String gTitle;

    @SerializedName("game_id")
    private Long gameId;

    @SerializedName("game_status")
    private String gameStatus;

    @SerializedName("platform")
    private String platform;

    @SerializedName("genre")
    private String genre;

    @SerializedName("description")
    private String description;

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
