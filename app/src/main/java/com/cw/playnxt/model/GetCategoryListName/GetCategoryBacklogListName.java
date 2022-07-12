
package com.cw.playnxt.model.GetCategoryListName;

import java.util.List;
import com.google.gson.annotations.Expose;


public class GetCategoryBacklogListName {

    @Expose
    private List<Backlog> backlog;

    public List<Backlog> getBacklog() {
        return backlog;
    }

    public void setBacklog(List<Backlog> backlog) {
        this.backlog = backlog;
    }

}
