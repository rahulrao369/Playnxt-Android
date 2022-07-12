
package com.cw.playnxt.model.ContactUs;

import com.google.gson.annotations.SerializedName;

public class ContactUsData {

    @SerializedName("contact_us")
    private String contactUs;

    public String getContactUs() {
        return contactUs;
    }

    public void setContactUs(String contactUs) {
        this.contactUs = contactUs;
    }

}
