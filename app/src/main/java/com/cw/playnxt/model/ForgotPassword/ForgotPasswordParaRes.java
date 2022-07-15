
package com.cw.playnxt.model.ForgotPassword;

import com.google.gson.annotations.Expose;

public class ForgotPasswordParaRes {

    @Expose
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
