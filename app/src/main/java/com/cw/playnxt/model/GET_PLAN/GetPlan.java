
package com.cw.playnxt.model.GET_PLAN;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPlan {

    @Expose
    private String createdAt;
    @Expose
    private Long id;
    @SerializedName("is_deleted")
    private Long isDeleted;
    @Expose
    private Long price;
    @Expose
    private String type;
    @Expose
    private String updatedAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
