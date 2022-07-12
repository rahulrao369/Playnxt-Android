
package com.cw.playnxt.model.FollowFriend;

import com.google.gson.annotations.Expose;

public class FollowFriendData {

    @Expose
    private Follower follower;

    public Follower getFollower() {
        return follower;
    }

    public void setFollower(Follower follower) {
        this.follower = follower;
    }

}
