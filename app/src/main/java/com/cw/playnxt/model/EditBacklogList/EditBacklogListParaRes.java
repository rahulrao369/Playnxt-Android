
package com.cw.playnxt.model.EditBacklogList;

import com.google.gson.annotations.SerializedName;

public class EditBacklogListParaRes {

    @SerializedName("backlog_id")
    private Long backlogId;
    @SerializedName("backlog_name")
    private String backlogName;

    public Long getBacklogId() {
        return backlogId;
    }

    public void setBacklogId(Long backlogId) {
        this.backlogId = backlogId;
    }

    public String getBacklogName() {
        return backlogName;
    }

    public void setBacklogName(String backlogName) {
        this.backlogName = backlogName;
    }

}
