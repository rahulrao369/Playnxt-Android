
package com.cw.playnxt.model.HomeSearch.BacklogSearch;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SearchBacklogData {

    @SerializedName("active_plan")
    private String mActivePlan;
    @SerializedName("result")
    private List<SearchBacklogDataResult> mSearchBacklogDataResult;
    @SerializedName("total_backlog")
    private Long mTotalBacklog;

    public String getActivePlan() {
        return mActivePlan;
    }

    public void setActivePlan(String activePlan) {
        mActivePlan = activePlan;
    }

    public List<SearchBacklogDataResult> getResult() {
        return mSearchBacklogDataResult;
    }

    public void setResult(List<SearchBacklogDataResult> searchBacklogDataResult) {
        mSearchBacklogDataResult = searchBacklogDataResult;
    }

    public Long getTotalBacklog() {
        return mTotalBacklog;
    }

    public void setTotalBacklog(Long totalBacklog) {
        mTotalBacklog = totalBacklog;
    }

}
