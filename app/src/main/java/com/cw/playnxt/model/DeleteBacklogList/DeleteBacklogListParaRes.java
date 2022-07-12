
package com.cw.playnxt.model.DeleteBacklogList;

import com.google.gson.annotations.SerializedName;

public class DeleteBacklogListParaRes {

    @SerializedName("list_id")
    private Long listId;
    @SerializedName("type")
    private String type;

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
