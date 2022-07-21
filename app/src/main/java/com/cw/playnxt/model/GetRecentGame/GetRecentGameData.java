
package com.cw.playnxt.model.GetRecentGame;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRecentGameData {
    @SerializedName("wish_count")
    private int wish_count;

    @SerializedName("backlog_count")
    private int backlog_count;

    @SerializedName("backlog_remaining")
    private int backlog_remaining;

    @SerializedName("active_plan")
    private String active_plan;

    public int getBacklog_remaining() {
        return backlog_remaining;
    }

    public void setBacklog_remaining(int backlog_remaining) {
        this.backlog_remaining = backlog_remaining;
    }

    public String getActive_plan() {
        return active_plan;
    }

    public void setActive_plan(String active_plan) {
        this.active_plan = active_plan;
    }

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
