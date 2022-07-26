
package com.cw.playnxt.model.GetCategoryListName;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetCategoryBacklogListName {

    @SerializedName("backlog_remain")
    private String backlog_remain;

    public String getBacklog_remain() {
        return backlog_remain;
    }

    public void setBacklog_remain(String backlog_remain) {
        this.backlog_remain = backlog_remain;
    }

    @Expose
    private List<Backlog> backlog;

    public List<Backlog> getBacklog() {
        return backlog;
    }

    public void setBacklog(List<Backlog> backlog) {
        this.backlog = backlog;
    }

}
