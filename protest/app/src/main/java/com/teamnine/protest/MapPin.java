package com.teamnine.protest;

import android.os.Parcel;
import android.os.Parcelable;

public class MapPin implements Parcelable {

    String type;
    String addr;
    String description;

    public MapPin(String type, String addr, String description) {
        this.type = type;
        this.addr = addr;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected MapPin(Parcel in) {
        type = in.readString();
        addr = in.readString();
        description = in.readString();
    }

    public static final Parcelable.Creator<MapPin> CREATOR = new Creator<MapPin>() {
        @Override
        public MapPin createFromParcel(Parcel in) {
            return new MapPin(in);
        }

        @Override
        public MapPin[] newArray(int size) {
            return new MapPin[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(addr);
        parcel.writeString(description);
    }
}
