
package com.cw.playnxt.model.CommunityData;

import java.util.List;
import com.google.gson.annotations.Expose;

public class GetCommunityListData {

    @Expose
    private List<CapsulCommunityData> capsul;

    public List<CapsulCommunityData> getCapsul() {
        return capsul;
    }

    public void setCapsul(List<CapsulCommunityData> capsul) {
        this.capsul = capsul;
    }

}
