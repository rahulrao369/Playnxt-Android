
package com.cw.playnxt.model.AddDownload;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AddDownloadReq {

    @SerializedName("device")
    private String mDevice;
    @SerializedName("device_id")
    private String mDeviceId;

    public String getDevice() {
        return mDevice;
    }

    public void setDevice(String device) {
        mDevice = device;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

}
