
package com.cw.playnxt.model.AddFriendGame;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFriendGameParaRes {

    @Expose
    private String category;
    @SerializedName("game_id")
    private Long gameId;
    @SerializedName("list_id")
    private Long listId;
    @SerializedName("list_name")
    private String listName;
    @Expose
    private Double rate;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

}
