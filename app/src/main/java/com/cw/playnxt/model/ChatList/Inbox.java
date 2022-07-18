
package com.cw.playnxt.model.ChatList;

import com.google.gson.annotations.Expose;

public class Inbox {

    @Expose
    private String image;
    @Expose
    private String message;
    @Expose
    private String name;
    @Expose
    private String time;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
