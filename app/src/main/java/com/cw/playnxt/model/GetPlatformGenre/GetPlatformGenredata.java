
package com.cw.playnxt.model.GetPlatformGenre;

import java.util.List;
import com.google.gson.annotations.Expose;

public class GetPlatformGenredata {

    @Expose
    private List<GenreList> genre;
    @Expose
    private List<PlatformList> platform;

    public List<GenreList> getGenre() {
        return genre;
    }

    public void setGenre(List<GenreList> genre) {
        this.genre = genre;
    }

    public List<PlatformList> getPlatform() {
        return platform;
    }

    public void setPlatform(List<PlatformList> platform) {
        this.platform = platform;
    }

}
