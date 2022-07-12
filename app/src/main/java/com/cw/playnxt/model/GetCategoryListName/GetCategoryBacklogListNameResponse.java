
package com.cw.playnxt.model.GetCategoryListName;


import com.google.gson.annotations.Expose;


public class GetCategoryBacklogListNameResponse {

    @Expose
    private GetCategoryBacklogListName data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetCategoryBacklogListName getData() {
        return data;
    }

    public void setData(GetCategoryBacklogListName data) {
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
