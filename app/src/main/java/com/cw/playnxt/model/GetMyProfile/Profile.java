
package com.cw.playnxt.model.GetMyProfile;

import com.google.gson.annotations.Expose;

public class Profile {

    @Expose
    private String email;
    @Expose
    private Long id;
    @Expose
    private String image;
    @Expose
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
