
package com.cw.playnxt.model.LoginSignup;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ResendVerificationReq {

    @SerializedName("email")
    private String mEmail;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

}
