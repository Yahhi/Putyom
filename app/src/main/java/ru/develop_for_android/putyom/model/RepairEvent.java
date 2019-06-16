package ru.develop_for_android.putyom.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RepairEvent {
    @SerializedName("id")
    public int contractNumber;

    @SerializedName("start")
    public Date start;

    @SerializedName("planEnd")
    public Date plannedEnd;

    @SerializedName("realEnd")
    public Date realEnd;

    @SerializedName("devices")
    public SmartDevice[] devices;

    @SerializedName("contractor")
    public Contractor worker;

    @SerializedName("rate")
    double rate;

    @SerializedName("images")
    public String[] imageAddresses;

    public RepairEvent(int contractNumber, Date start, Date plannedEnd, Date realEnd, SmartDevice[] devices, Contractor worker, double rate, String[] imageAddresses) {
        this.contractNumber = contractNumber;
        this.start = start;
        this.plannedEnd = plannedEnd;
        this.realEnd = realEnd;
        this.devices = devices;
        this.worker = worker;
        this.rate = rate;
        this.imageAddresses = imageAddresses;
    }
}
