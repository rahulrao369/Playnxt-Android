
package com.cw.playnxt.model.AddGameStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddGameStatusParaRes {

    @SerializedName("game_id")
    private Long gameId;
    @Expose
    private String status;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
