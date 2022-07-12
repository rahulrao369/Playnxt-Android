
package com.cw.playnxt.model.GetBacklogList;

import java.util.List;
import com.google.gson.annotations.Expose;


public class GetMyBacklogListData {

    @Expose
    private List<Count> count;

    public List<Count> getCount() {
        return count;
    }

    public void setCount(List<Count> count) {
        this.count = count;
    }

}
