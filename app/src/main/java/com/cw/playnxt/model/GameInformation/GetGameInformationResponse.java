
package com.cw.playnxt.model.GameInformation;

import com.google.gson.annotations.Expose;


public class GetGameInformationResponse {

    @Expose
    private GetGameInformationData data;
    @Expose
    private String message;
    @Expose
    private Boolean status;

    public GetGameInformationData getData() {
        return data;
    }

    public void setData(GetGameInformationData data) {
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
