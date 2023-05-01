
package com.cw.playnxt.model.HomeSearch.BacklogSearch;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SearchBacklogDataResult {

    @SerializedName("id")
    private Long mId;
    @SerializedName("list_name")
    private String mListName;
    @SerializedName("total_game")
    private Long mTotalGame;
    @SerializedName("type")
    private String mType;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getListName() {
        return mListName;
    }

    public void setListName(String listName) {
        mListName = listName;
    }

    public Long getTotalGame() {
        return mTotalGame;
    }

    public void setTotalGame(Long totalGame) {
        mTotalGame = totalGame;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
