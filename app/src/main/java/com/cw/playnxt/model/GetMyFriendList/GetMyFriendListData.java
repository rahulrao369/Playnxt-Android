
package com.cw.playnxt.model.GetMyFriendList;

import java.util.List;
import com.google.gson.annotations.Expose;

public class GetMyFriendListData {

    @Expose
    private List<GetMyFriendListDataCapsul> capsul;

    public List<GetMyFriendListDataCapsul> getCapsul() {
        return capsul;
    }

    public void setCapsul(List<GetMyFriendListDataCapsul> capsul) {
        this.capsul = capsul;
    }

}
