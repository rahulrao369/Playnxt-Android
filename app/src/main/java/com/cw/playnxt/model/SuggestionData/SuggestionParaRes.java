
package com.cw.playnxt.model.SuggestionData;

import com.google.gson.annotations.Expose;

public class SuggestionParaRes {

    @Expose
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
