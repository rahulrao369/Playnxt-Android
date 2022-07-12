
package com.cw.playnxt.model.ContactUs;

import com.google.gson.annotations.Expose;

public class ContactUsResponse {

    @Expose
    private ContactUsData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public ContactUsData getData() {
        return data;
    }

    public void setData(ContactUsData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
