
package com.cw.playnxt.model.GetGameNote;

import com.google.gson.annotations.SerializedName;

public class GetGameNoteParaRes {

    @SerializedName("game_id")
    private Long gameId;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

}
