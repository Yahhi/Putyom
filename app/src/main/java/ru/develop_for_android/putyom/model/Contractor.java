package ru.develop_for_android.putyom.model;

import com.google.gson.annotations.SerializedName;

public class Contractor {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    public Contractor(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
