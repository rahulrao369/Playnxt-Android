
package com.cw.playnxt.model.HomeSearch.UserSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SearchUserDataResult {

    @Expose
    private String createdAt;
    @SerializedName("device_token")
    private String deviceToken;
    @Expose
    private String email;
    @SerializedName("fcm_token")
    private String fcmToken;
    @Expose
    private Long id;
    @Expose
    private String image;
    @SerializedName("is_deleted")
    private Long isDeleted;
    @Expose
    private String name;
    @Expose
    private String password;
    @SerializedName("remember_token")
    private Object rememberToken;
    @Expose
    private Long role;
    @SerializedName("special_price_notification")
    private Long specialPriceNotification;
    @Expose
    private String status;
    @Expose
    private String updatedAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public Long getSpecialPriceNotification() {
        return specialPriceNotification;
    }

    public void setSpecialPriceNotification(Long specialPriceNotification) {
        this.specialPriceNotification = specialPriceNotification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
