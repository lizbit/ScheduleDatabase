package com.example.robertcockrell.scheduledatabase;

/**
 * Created by elizabeth.quick on 2/16/2015.
 */
public class EventItem {
    private long id;
    private String eventName;
    private String eventTime;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getEventName(){
        return eventName;
    }

    public void setEventName(String name){
        this.eventName = name;
    }

    public String getEventTime(){
        return eventTime;
    }

    public void setEventTime(String time){
        this.eventTime = time;
    }

    public String toString(){
        return eventName + " - " + eventTime;
    }
}
