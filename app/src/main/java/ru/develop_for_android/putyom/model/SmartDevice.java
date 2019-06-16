package ru.develop_for_android.putyom.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SmartDevice implements Parcelable {
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

    protected SmartDevice(Parcel in) {
        id = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        workId = in.readInt();
    }

    public static final Creator<SmartDevice> CREATOR = new Creator<SmartDevice>() {
        @Override
        public SmartDevice createFromParcel(Parcel in) {
            return new SmartDevice(in);
        }

        @Override
        public SmartDevice[] newArray(int size) {
            return new SmartDevice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeInt(workId);
    }

    public String getCoordinates() {
        return "Latitude: " + latitude + "\nLongitude: " + longitude;
    }
}
