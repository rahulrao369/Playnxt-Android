
package com.cw.playnxt.model.GetRecentGame;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRecentGameData {
    @SerializedName("wish_count")
    private int wish_count;

    @SerializedName("backlog_count")
    private int backlog_count;
    @Expose
    private List<GetRecentGameDataCapsul> capsul;

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public int getBacklog_count() {
        return backlog_count;
    }

    public void setBacklog_count(int backlog_count) {
        this.backlog_count = backlog_count;
    }

    public List<GetRecentGameDataCapsul> getCapsul() {
        return capsul;
    }

    public void setCapsul(List<GetRecentGameDataCapsul> capsul) {
        this.capsul = capsul;
    }

}
