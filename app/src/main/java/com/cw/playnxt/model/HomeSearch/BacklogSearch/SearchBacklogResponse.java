
package com.cw.playnxt.model.HomeSearch.BacklogSearch;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SearchBacklogResponse {

    @SerializedName("data")
    private SearchBacklogData mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public SearchBacklogData getData() {
        return mData;
    }

    public void setData(SearchBacklogData data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
