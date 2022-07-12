
package com.cw.playnxt.model.HomeData;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @Expose
    private List<Following> following;
    @Expose
    private Profile profile;
    @SerializedName("totel_following")
    private Long totelFollowing;

    public List<Following> getFollowing() {
        return following;
    }

    public void setFollowing(List<Following> following) {
        this.following = following;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Long getTotelFollowing() {
        return totelFollowing;
    }

    public void setTotelFollowing(Long totelFollowing) {
        this.totelFollowing = totelFollowing;
    }

}
