
package com.cw.playnxt.model.ViewMyBacklogGame;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;


public class ViewMyBacklogGameData implements Serializable {

    @Expose
    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

}
