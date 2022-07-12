
package com.cw.playnxt.model.ViewMyWishlistGame;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;

public class ViewMyWishlistGameData implements Serializable {

    @Expose
    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

}
