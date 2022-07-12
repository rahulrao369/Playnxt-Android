
package com.cw.playnxt.model.EditCalenderEvent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditCalenderEventParaRes {

    @SerializedName("end_date")
    private String endDate;
    @SerializedName("event_id")
    private String eventId;
    @Expose
    private String note;
    @SerializedName("start_date")
    private String startDate;
    @Expose
    private String title;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
