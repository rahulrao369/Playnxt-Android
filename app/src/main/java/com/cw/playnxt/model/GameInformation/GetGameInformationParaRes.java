
package com.cw.playnxt.model.GameInformation;

import com.google.gson.annotations.SerializedName;

public class GetGameInformationParaRes {

    @SerializedName("game_id")
    private Long gameId;

 @SerializedName("game_view")
    private String game_view;

    public String getGame_view() {
        return game_view;
    }

    public void setGame_view(String game_view) {
        this.game_view = game_view;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

}
