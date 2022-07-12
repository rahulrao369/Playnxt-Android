
package com.cw.playnxt.model.GetGameNote;

import java.util.List;
import com.google.gson.annotations.Expose;

public class GetGameNoteData {

    @Expose
    private List<Capsul> capsul;

    public List<Capsul> getCapsul() {
        return capsul;
    }

    public void setCapsul(List<Capsul> capsul) {
        this.capsul = capsul;
    }

}
