package com.teamnine.protest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;

public class ProtestEvent implements Parcelable {
    String id;
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
    ArrayList<MapPin> pinList;

    public ProtestEvent() {}

    public ProtestEvent(String id, String name, String location, String startDate, String endDate, String description, String owner) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.owner = owner;
        this.pinList = new ArrayList<MapPin>();
    }

    /* SETTERS AND GETTERS START */
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getSentiment() {
        return sentiment;
    }
    public void setSentiment(int sentiment) {
        this.sentiment = sentiment;
    }

    public String getRoute() {
        return route;
    }
    public void setRoute(String route) {
        this.route = route;
    }

    public String getRouteDescription() {
        return routeDescription;
    }
    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public ArrayList<MapPin> getPinList() { return pinList; }
    public void setPinList(ArrayList<MapPin> pinList) { this.pinList = pinList; }
    /* SETTERS AND GETTERS END*/

    protected ProtestEvent(Parcel in) {
        id = in.readString();
        name = in.readString();
        location = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        description = in.readString();
        owner = in.readString();
        route = in.readString();
        routeDescription = in.readString();
        sentiment = in.readInt();
        in.readTypedList(pinList, MapPin.CREATOR);
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
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeString(description);
        parcel.writeString(owner);
        parcel.writeString(route);
        parcel.writeString(routeDescription);
        parcel.writeInt(sentiment);
        parcel.writeTypedList(pinList);
    }

    public void addPin (MapPin newPin) {
        this.pinList.add(newPin);
    }
}
