package com.teamnine.protest;

import android.os.Parcel;
import android.os.Parcelable;

public class ProtestFeed implements Parcelable {
    String id;
    String event_id;
    String description;
    String owner;
    String time;

    public ProtestFeed() {}

    public ProtestFeed(String id, String event_id, String description, String owner, String time) {
        this.id = id;
        this.event_id = event_id;
        this.description = description;
        this.owner = owner;
        this.time = time;
    }

    /* SETTERS AND GETTERS START */
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getEvent_id() {
        return event_id;
    }
    public void setEvent_id(String event_id) {
        this.event_id = event_id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /* SETTERS AND GETTERS END*/

    protected ProtestFeed(Parcel in) {
        id = in.readString();
        event_id = in.readString();
        description = in.readString();
        owner = in.readString();
        time = in.readString();
    }

    public static final Creator<ProtestFeed> CREATOR = new Creator<ProtestFeed>() {
        @Override
        public ProtestFeed createFromParcel(Parcel in) {
            return new ProtestFeed(in);
        }

        @Override
        public ProtestFeed[] newArray(int size) {
            return new ProtestFeed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(event_id);
        parcel.writeString(description);
        parcel.writeString(owner);
        parcel.writeString(time);
    }
}