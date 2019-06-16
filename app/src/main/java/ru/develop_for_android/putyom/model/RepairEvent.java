package ru.develop_for_android.putyom.model;

import com.google.gson.annotations.SerializedName;

public class RepairEvent {
    @SerializedName("id")
    public int contractNumber;

    @SerializedName("name")
    public String address;

    @SerializedName("created")
    public String created;

    @SerializedName("plannedStart")
    public String plannedStart;

    @SerializedName("actualStart")
    public String start;

    @SerializedName("planEnd")
    public String plannedEnd;

    @SerializedName("actualEnd")
    public String realEnd;

    @SerializedName("devices")
    public SmartDevice[] devices;

    @SerializedName("contractorId")
    public int worker;

    @SerializedName("rate")
    public double rate;

    @SerializedName("plannedStartLat")
    double plannedStartLatitude;
    @SerializedName("plannedStartLng")
    double plannedStartLongitude;

    @SerializedName("plannedEndLat")
    double plannedEndLatitude;
    @SerializedName("plannedEndLng")
    double plannedEndLongitude;

    @SerializedName("cameraInfos")
    public String[] imageAddresses;

    public RepairEvent(int contractNumber, String address, String created, String plannedStart, String start, String plannedEnd, String realEnd, SmartDevice[] devices, int worker, double rate, double plannedStartLatitude, double plannedStartLongitude, double plannedEndLatitude, double plannedEndLongitude, String[] imageAddresses) {
        this.contractNumber = contractNumber;
        this.address = address;
        this.created = created;
        this.plannedStart = plannedStart;
        this.start = start;
        this.plannedEnd = plannedEnd;
        this.realEnd = realEnd;
        this.devices = devices;
        this.worker = worker;
        this.rate = rate;
        this.plannedStartLatitude = plannedStartLatitude;
        this.plannedStartLongitude = plannedStartLongitude;
        this.plannedEndLatitude = plannedEndLatitude;
        this.plannedEndLongitude = plannedEndLongitude;
        this.imageAddresses = imageAddresses;
    }

    public String getPlannedDates() {
        return getVisibleDate(plannedStart) + " - " + getVisibleDate(plannedEnd);
    }

    public String getFactDates() {
        return getVisibleDate(start) + " - " + getVisibleDate(realEnd);
    }

    private String getVisibleDate(String date) {
        if (date == null || date.startsWith("0001")) {
            return "---";
        }
        return date.substring(0, plannedStart.indexOf("T"));
    }
}
