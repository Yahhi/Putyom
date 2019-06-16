package ru.develop_for_android.putyom.model;

import com.google.gson.annotations.SerializedName;

public class SmartDevice {
    @SerializedName("id")
    public int id;
    @SerializedName("lat")
    public double latitude;
    @SerializedName("lng")
    public double longitude;
    @SerializedName("workId")
    public int workId;

    public SmartDevice(int id, double latitude, double longitude, int workId) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.workId = workId;
    }
}
