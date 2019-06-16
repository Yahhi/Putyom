package ru.develop_for_android.putyom.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RepairEvent implements Parcelable {
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

    protected RepairEvent(Parcel in) {
        devices = in.createTypedArray(SmartDevice.CREATOR);
        contractNumber = in.readInt();
        address = in.readString();
        created = in.readString();
        plannedStart = in.readString();
        start = in.readString();
        plannedEnd = in.readString();
        realEnd = in.readString();
        worker = in.readInt();
        rate = in.readDouble();
        plannedStartLatitude = in.readDouble();
        plannedStartLongitude = in.readDouble();
        plannedEndLatitude = in.readDouble();
        plannedEndLongitude = in.readDouble();
        imageAddresses = in.createStringArray();
    }

    public static final Creator<RepairEvent> CREATOR = new Creator<RepairEvent>() {
        @Override
        public RepairEvent createFromParcel(Parcel in) {
            return new RepairEvent(in);
        }

        @Override
        public RepairEvent[] newArray(int size) {
            return new RepairEvent[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(devices, 0);
        dest.writeInt(contractNumber);
        dest.writeString(address);
        dest.writeString(created);
        dest.writeString(plannedStart);
        dest.writeString(start);
        dest.writeString(plannedEnd);
        dest.writeString(realEnd);
        dest.writeInt(worker);
        dest.writeDouble(rate);
        dest.writeDouble(plannedStartLatitude);
        dest.writeDouble(plannedStartLongitude);
        dest.writeDouble(plannedEndLatitude);
        dest.writeDouble(plannedEndLongitude);
        dest.writeStringArray(imageAddresses);
    }
}
