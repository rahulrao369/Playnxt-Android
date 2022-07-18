
package com.cw.playnxt.model.ChatList;

import com.google.gson.annotations.Expose;


public class ChatListResponse {

    @Expose
    private ChatListData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public ChatListData getData() {
        return data;
    }

    public void setData(ChatListData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
