package ru.develop_for_android.putyom.model;

import com.google.gson.annotations.SerializedName;

public class SmartDevice {
    @SerializedName("id")
    long id;
    @SerializedName("latitude")
    double latitude;
    @SerializedName("longitude")
    double longitude;
    @SerializedName("start")
    boolean startPoint;

    public SmartDevice(long id, double latitude, double longitude, boolean startPoint) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startPoint = startPoint;
    }
}
