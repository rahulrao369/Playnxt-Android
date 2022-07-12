package com.cw.playnxt.model.StaticModel;

public class GameModel {
    int game_img;
    String game_name;
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public GameModel(int game_img, String game_name) {
        this.game_img = game_img;
        this.game_name = game_name;
    }

    public int getGame_img() {
        return game_img;
    }

    public void setGame_img(int game_img) {
        this.game_img = game_img;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }
}
