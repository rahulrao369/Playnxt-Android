package com.cw.playnxt.Interface;

import com.cw.playnxt.model.CalenderDataModel.GetEvent.Event;

public interface ItemClickEditCalenderEvent {
    public void onItemClick(int position, String type, Event eventData);
}
