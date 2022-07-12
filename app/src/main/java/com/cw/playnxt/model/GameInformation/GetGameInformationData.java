
package com.cw.playnxt.model.GameInformation;

import com.google.gson.annotations.Expose;

public class GetGameInformationData {

    @Expose
    private Capsul capsul;

    public Capsul getCapsul() {
        return capsul;
    }

    public void setCapsul(Capsul capsul) {
        this.capsul = capsul;
    }

}
