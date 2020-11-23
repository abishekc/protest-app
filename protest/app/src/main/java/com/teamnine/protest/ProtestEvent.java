package com.teamnine.protest;

import java.util.Calendar;

public class ProtestEvent {
    String name;
    String location;
    Calendar startDate;
    Calendar endDate;
    String description;
    String owner;
//    For later use
    String route = null;
    String routeDescription = null;

    public ProtestEvent(String name, String location, Calendar startDate, Calendar endDate, String description, String owner) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.owner = owner;
    }
}
