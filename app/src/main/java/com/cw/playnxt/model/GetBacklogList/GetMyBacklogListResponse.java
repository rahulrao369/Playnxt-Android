
package com.cw.playnxt.model.GetBacklogList;

import com.google.gson.annotations.Expose;

public class GetMyBacklogListResponse {

    @Expose
    private GetMyBacklogListData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetMyBacklogListData getData() {
        return data;
    }

    public void setData(GetMyBacklogListData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
