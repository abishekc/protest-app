package com.teamnine.protest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class ProtestEvent implements Parcelable {
    String name;
    String location;
    String startDate;
    String endDate;
    String description;
    String owner;
    int sentiment;
//    For later use
    String route = null;
    String routeDescription = null;

    public ProtestEvent(String name, String location, String startDate, String endDate, String description, String owner) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.owner = owner;
    }

    public void setRouteDescription(String routeDes) {
        routeDescription = routeDes;
    }

    public void setSentiment(int score) {
        sentiment = score;
    }

    protected ProtestEvent(Parcel in) {
        name = in.readString();
        location = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        description = in.readString();
        owner = in.readString();
        route = in.readString();
        routeDescription = in.readString();
        sentiment = in.readInt();
    }

    public static final Creator<ProtestEvent> CREATOR = new Creator<ProtestEvent>() {
        @Override
        public ProtestEvent createFromParcel(Parcel in) {
            return new ProtestEvent(in);
        }

        @Override
        public ProtestEvent[] newArray(int size) {
            return new ProtestEvent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeString(description);
        parcel.writeString(owner);
        parcel.writeString(route);
        parcel.writeString(routeDescription);
        parcel.writeInt(sentiment);
    }
}
