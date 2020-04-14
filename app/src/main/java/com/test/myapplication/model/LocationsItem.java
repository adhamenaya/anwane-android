package com.test.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LocationsItem implements Parcelable {
    public static final Parcelable.Creator<LocationsItem> CREATOR = new Parcelable.Creator<LocationsItem>() {
        public LocationsItem createFromParcel(Parcel in) {
            return new LocationsItem(in);
        }

        public LocationsItem[] newArray(int size) {
            return new LocationsItem[size];
        }
    };
    @SerializedName("latlon")
    private String latlon;
    private String code;

    public LocationsItem() {
    }

    public LocationsItem(Parcel in) {
        latlon = in.readString();
        code = in.readString();
    }

    @Override
    public String toString() {
        return "LocationsItem{" +
                "latlon='" + latlon + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(latlon);
        dest.writeString(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLatlon() {
        return latlon;
    }

    public void setLatlon(String latlon) {
        this.latlon = latlon;
    }

}