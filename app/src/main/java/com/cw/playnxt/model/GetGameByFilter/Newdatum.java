
package com.cw.playnxt.model.GetGameByFilter;

import java.util.List;
import com.google.gson.annotations.Expose;

public class Newdatum {

    @Expose
    private String description;
    @Expose
    private List<String> genre;
    @Expose
    private Long id;
    @Expose
    private String image;
    @Expose
    private List<String> platform;
    @Expose
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getPlatform() {
        return platform;
    }

    public void setPlatform(List<String> platform) {
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
