
package com.cw.playnxt.model.LoginSignup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupParaRes {

    @SerializedName("device_token")
    private String deviceToken;
    @Expose
    private String email;
    @SerializedName("fcm_token")
    private String fcmToken;
    @Expose
    private String password;
    @Expose
    private Long role;
    @SerializedName("user_name")
    private String userName;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
