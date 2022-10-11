package com.cw.playnxt.model.ViewMyBacklogGame;

import com.google.gson.annotations.SerializedName;

public class ViewMyBacklogGameParaRes {

    @SerializedName("list_id")
    private Long listId;

    @SerializedName("keyword")
    private String keyword;

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
