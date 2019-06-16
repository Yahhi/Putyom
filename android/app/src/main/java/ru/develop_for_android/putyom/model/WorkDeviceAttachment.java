package ru.develop_for_android.putyom.model;

import com.google.gson.annotations.SerializedName;

public class WorkDeviceAttachment {

    @SerializedName("WorkId")
    public int workId;

    @SerializedName("SignId")
    public int deviceId;

    public WorkDeviceAttachment(int workId, int deviceId) {
        this.workId = workId;
        this.deviceId = deviceId;
    }
}
