
package com.cw.playnxt.model.AboutUs;

import com.google.gson.annotations.SerializedName;

public class AboutUsData {

    @SerializedName("about_app")
    private String aboutApp;

    public String getAboutApp() {
        return aboutApp;
    }

    public void setAboutApp(String aboutApp) {
        this.aboutApp = aboutApp;
    }

}
