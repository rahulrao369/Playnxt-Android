
package com.cw.playnxt.model.GetWishlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Count {
    @Expose
    private Long id;
    @SerializedName("list_name")
    private String listName;
    @SerializedName("total_game")
    private Long totalGame;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Long getTotalGame() {
        return totalGame;
    }

    public void setTotalGame(Long totalGame) {
        this.totalGame = totalGame;
    }

}
