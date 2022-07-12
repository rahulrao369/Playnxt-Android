
package com.cw.playnxt.model.EditGameNote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditGameNoteParaRes {

    @SerializedName("game_id")
    private Long gameId;
    @Expose
    private String newnote;
    @SerializedName("note_id")
    private Long noteId;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getNewnote() {
        return newnote;
    }

    public void setNewnote(String newnote) {
        this.newnote = newnote;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

}
