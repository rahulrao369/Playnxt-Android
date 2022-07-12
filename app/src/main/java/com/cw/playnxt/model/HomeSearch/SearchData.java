
package com.cw.playnxt.model.HomeSearch;

import java.util.List;
import com.google.gson.annotations.Expose;

public class SearchData {

    @Expose
    private List<SearchFollowing> following;
    @Expose
    private List<SearchGame> games;
    @Expose
    private SearchProfile profile;

    public List<SearchFollowing> getFollowing() {
        return following;
    }

    public void setFollowing(List<SearchFollowing> following) {
        this.following = following;
    }

    public List<SearchGame> getGames() {
        return games;
    }

    public void setGames(List<SearchGame> games) {
        this.games = games;
    }

    public SearchProfile getProfile() {
        return profile;
    }

    public void setProfile(SearchProfile profile) {
        this.profile = profile;
    }

}
