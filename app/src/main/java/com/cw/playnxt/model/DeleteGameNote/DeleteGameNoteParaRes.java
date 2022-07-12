
package com.cw.playnxt.model.DeleteGameNote;

import com.google.gson.annotations.SerializedName;


public class DeleteGameNoteParaRes {

    @SerializedName("game_id")
    private Long gameId;
    @SerializedName("note_id")
    private Long noteId;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

}
