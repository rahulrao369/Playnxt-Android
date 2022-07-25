
package com.cw.playnxt.model.GetBacklogList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetMyBacklogListData {

    @SerializedName("backlog_remain")
    private String backlog_remain;

    public String getBacklog_remain() {
        return backlog_remain;
    }

    public void setBacklog_remain(String backlog_remain) {
        this.backlog_remain = backlog_remain;
    }

    @Expose
    private List<Count> count;

    public List<Count> getCount() {
        return count;
    }

    public void setCount(List<Count> count) {
        this.count = count;
    }

}
