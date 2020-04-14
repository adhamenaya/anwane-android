package com.test.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DeliveryPlanItem implements Parcelable {

    public static final Parcelable.Creator<DeliveryPlanItem> CREATOR = new Parcelable.Creator<DeliveryPlanItem>() {
        public DeliveryPlanItem createFromParcel(Parcel in) {
            return new DeliveryPlanItem(in);
        }

        public DeliveryPlanItem[] newArray(int size) {
            return new DeliveryPlanItem[size];
        }
    };
    @SerializedName("latlon_to")
    private String latlonTo;
    @SerializedName("distance")
    private double distance;
    @SerializedName("latlon_from")
    private String latlonFrom;
    @SerializedName("node_from")
    private int nodeFrom;
    @SerializedName("node_to")
    private int nodeTo;

    public DeliveryPlanItem(Parcel in) {
        latlonTo = in.readString();
        distance = in.readDouble();
        latlonFrom = in.readString();
        nodeFrom = in.readInt();
        nodeTo = in.readInt();

    }

    public String getLatlonTo() {
        return latlonTo;
    }

    public void setLatlonTo(String latlonTo) {
        this.latlonTo = latlonTo;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getLatlonFrom() {
        return latlonFrom;
    }

    public void setLatlonFrom(String latlonFrom) {
        this.latlonFrom = latlonFrom;
    }

    public int getNodeFrom() {
        return nodeFrom;
    }

    public void setNodeFrom(int nodeFrom) {
        this.nodeFrom = nodeFrom;
    }

    public int getNodeTo() {
        return nodeTo;
    }

    public void setNodeTo(int nodeTo) {
        this.nodeTo = nodeTo;
    }

    @Override
    public String toString() {
        return
                "DeliveryPlanItem{" +
                        "latlon_to = '" + latlonTo + '\'' +
                        ",distance = '" + distance + '\'' +
                        ",latlon_from = '" + latlonFrom + '\'' +
                        ",node_from = '" + nodeFrom + '\'' +
                        ",node_to = '" + nodeTo + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(latlonTo);
        dest.writeDouble(distance);
        dest.writeString(latlonFrom);
        dest.writeInt(nodeFrom);
        dest.writeInt(nodeTo);
    }
}