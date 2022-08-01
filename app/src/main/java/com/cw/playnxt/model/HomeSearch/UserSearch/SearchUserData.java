
package com.cw.playnxt.model.HomeSearch.UserSearch;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchUserData {

    @SerializedName("active_plan")
    private String activePlan;
    @Expose
    private List<SearchUserDataResult> result;
    @SerializedName("total_backlog")
    private Long totalBacklog;

    public String getActivePlan() {
        return activePlan;
    }

    public void setActivePlan(String activePlan) {
        this.activePlan = activePlan;
    }

    public List<SearchUserDataResult> getResult() {
        return result;
    }

    public void setResult(List<SearchUserDataResult> result) {
        this.result = result;
    }

    public Long getTotalBacklog() {
        return totalBacklog;
    }

    public void setTotalBacklog(Long totalBacklog) {
        this.totalBacklog = totalBacklog;
    }

}
