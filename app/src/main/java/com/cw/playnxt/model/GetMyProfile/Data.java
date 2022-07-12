
package com.cw.playnxt.model.GetMyProfile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @Expose
    private List<Follower> follower;
    @Expose
    private List<Following> following;
    @Expose
    private List<Game> games;
    @Expose
    private Profile profile;
    @SerializedName("totel_follower")
    private Long totelFollower;
    @SerializedName("totel_following")
    private Long totelFollowing;
    @SerializedName("totel_game")
    private Long totelGame;

    public List<Follower> getFollower() {
        return follower;
    }

    public void setFollower(List<Follower> follower) {
        this.follower = follower;
    }

    public List<Following> getFollowing() {
        return following;
    }

    public void setFollowing(List<Following> following) {
        this.following = following;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Long getTotelFollower() {
        return totelFollower;
    }

    public void setTotelFollower(Long totelFollower) {
        this.totelFollower = totelFollower;
    }

    public Long getTotelFollowing() {
        return totelFollowing;
    }

    public void setTotelFollowing(Long totelFollowing) {
        this.totelFollowing = totelFollowing;
    }

    public Long getTotelGame() {
        return totelGame;
    }

    public void setTotelGame(Long totelGame) {
        this.totelGame = totelGame;
    }

}
