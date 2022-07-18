
package com.cw.playnxt.model.ChatList;

import java.util.List;
import com.google.gson.annotations.Expose;

public class ChatListData {

    @Expose
    private List<Inbox> inbox;

    public List<Inbox> getInbox() {
        return inbox;
    }

    public void setInbox(List<Inbox> inbox) {
        this.inbox = inbox;
    }

}
