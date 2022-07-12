
package com.cw.playnxt.model.LoginSignup;

import com.google.gson.annotations.Expose;

public class LoginResponse {

    @Expose
    private LoginData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
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
