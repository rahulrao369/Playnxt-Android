
package com.cw.playnxt.model.ChangePassword;

import com.google.gson.annotations.Expose;

public class ChangePasswordParaRes {

    @Expose
    private String newpass;
    @Expose
    private String oldpass;

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public String getOldpass() {
        return oldpass;
    }

    public void setOldpass(String oldpass) {
        this.oldpass = oldpass;
    }

}
