
package com.cw.playnxt.model.DeleteCalenderEvent;

import com.google.gson.annotations.SerializedName;

public class DeleteCalenderEventParaRes {

    @SerializedName("event_id")
    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

}
