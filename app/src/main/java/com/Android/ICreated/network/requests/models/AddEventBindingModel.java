package com.Android.ICreated.network.requests.models;

/**
 * Created by Филипп on 19.04.2015.
 */
public class AddEventBindingModel {
    private String Latitude;

    private String Longitude;

    private String Description;

    private String EventDate;

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDescription() {
        return Description;
    }

    public void setEventDate(String EventDate) {
        this.EventDate = EventDate;
    }

    public String getEventDate() {
        return EventDate;
    }
}
