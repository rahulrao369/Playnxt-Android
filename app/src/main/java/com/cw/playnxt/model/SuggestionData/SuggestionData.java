
package com.cw.playnxt.model.SuggestionData;

import com.google.gson.annotations.Expose;

public class SuggestionData {

    @Expose
    private Suggest suggest;

    public Suggest getSuggest() {
        return suggest;
    }

    public void setSuggest(Suggest suggest) {
        this.suggest = suggest;
    }

}
