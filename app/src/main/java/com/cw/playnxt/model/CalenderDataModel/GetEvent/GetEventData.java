
package com.cw.playnxt.model.CalenderDataModel.GetEvent;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;

public class GetEventData implements Serializable {

    @Expose
    private List<Event> event;

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

}
