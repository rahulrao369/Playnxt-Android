
package com.cw.playnxt.model.GetMyFriendProfile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMyFriendProfileData {

    @Expose
    private List<MyFriendProfileFollower> follower;
    @Expose
    private List<MyFriendProfileFollowing> following;
    @Expose
    private List<MyFriendProfileGame> games;
    @Expose
    private MyFriendProfile profile;

    @SerializedName("totel_follower")
    private Long totelFollower;

    @SerializedName("totel_following")
    private Long totelFollowing;

    @SerializedName("totel_game")
    private Long totelGame;

    public List<MyFriendProfileFollower> getFollower() {
        return follower;
    }

    public void setFollower(List<MyFriendProfileFollower> follower) {
        this.follower = follower;
    }

    public List<MyFriendProfileFollowing> getFollowing() {
        return following;
    }

    public void setFollowing(List<MyFriendProfileFollowing> following) {
        this.following = following;
    }

    public List<MyFriendProfileGame> getGames() {
        return games;
    }

    public void setGames(List<MyFriendProfileGame> games) {
        this.games = games;
    }

    public MyFriendProfile getProfile() {
        return profile;
    }

    public void setProfile(MyFriendProfile profile) {
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
