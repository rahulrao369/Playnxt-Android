
package com.cw.playnxt.model.StaffPicks;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("affiliate_link")
    private String affiliateLink;
    @Expose
    private List<Capsul> capsul;

    public String getAffiliateLink() {
        return affiliateLink;
    }

    public void setAffiliateLink(String affiliateLink) {
        this.affiliateLink = affiliateLink;
    }

    public List<Capsul> getCapsul() {
        return capsul;
    }

    public void setCapsul(List<Capsul> capsul) {
        this.capsul = capsul;
    }

}
