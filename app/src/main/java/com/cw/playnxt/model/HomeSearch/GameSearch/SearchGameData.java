
package com.cw.playnxt.model.HomeSearch.GameSearch;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchGameData {

    @SerializedName("active_plan")
    private String activePlan;
    @Expose
    private List<SearchGameDataResult> result;
    @SerializedName("total_backlog")
    private Long totalBacklog;

    public String getActivePlan() {
        return activePlan;
    }

    public void setActivePlan(String activePlan) {
        this.activePlan = activePlan;
    }

    public List<SearchGameDataResult> getResult() {
        return result;
    }

    public void setResult(List<SearchGameDataResult> result) {
        this.result = result;
    }

    public Long getTotalBacklog() {
        return totalBacklog;
    }

    public void setTotalBacklog(Long totalBacklog) {
        this.totalBacklog = totalBacklog;
    }

}
