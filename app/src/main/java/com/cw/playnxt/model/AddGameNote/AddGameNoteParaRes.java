
package com.cw.playnxt.model.AddGameNote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddGameNoteParaRes {

    @SerializedName("game_id")
    private Long gameId;
    @Expose
    private String note;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
