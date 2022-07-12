
package com.cw.playnxt.model.DeleteGame;

import com.google.gson.annotations.SerializedName;

public class DeleteGameParaRes {

    @SerializedName("game_id")
    private Long gameId;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

}
