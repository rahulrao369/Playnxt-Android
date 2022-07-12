
package com.cw.playnxt.model.GetGameNote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Capsul {

    @Expose
    private String createOn;
    @SerializedName("game_id")
    private Long gameId;
    @Expose
    private String note;
    @Expose
    private Long noteId;

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }

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

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

}
