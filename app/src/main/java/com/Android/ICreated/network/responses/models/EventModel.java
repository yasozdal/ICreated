package com.Android.ICreated.network.responses.models;

/**
 * Created by ‘ËÎËÔÔ on 20.04.2015.
 */


import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "EventId",
        "User",
        "Latitude",
        "Longitude",
        "LocationCaption",
        "Description",
        "EventDate",
        "DateCreate",
        "LastComments",
        "Photos",
        "LastLikes"
})
public class EventModel {

    @JsonProperty("EventId")
    private Integer EventId;
    @JsonProperty("User")
    private com.Android.ICreated.network.responses.models.User User;
    @JsonProperty("Latitude")
    private String Latitude;
    @JsonProperty("Longitude")
    private String Longitude;
    @JsonProperty("LocationCaption")
    private String LocationCaption;
    @JsonProperty("Description")
    private String Description;
    @JsonProperty("EventDate")
    private String EventDate;
    @JsonProperty("DateCreate")
    private String DateCreate;
    @JsonProperty("LastComments")
    private Object LastComments;
    @JsonProperty("Photos")
    private Object Photos;
    @JsonProperty("LastLikes")
    private Object LastLikes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The EventId
     */
    @JsonProperty("EventId")
    public Integer getEventId() {
        return EventId;
    }

    /**
     *
     * @param EventId
     * The EventId
     */
    @JsonProperty("EventId")
    public void setEventId(Integer EventId) {
        this.EventId = EventId;
    }

    /**
     *
     * @return
     * The User
     */
    @JsonProperty("User")
    public com.Android.ICreated.network.responses.models.User getUser() {
        return User;
    }

    /**
     *
     * @param User
     * The User
     */
    @JsonProperty("User")
    public void setUser(com.Android.ICreated.network.responses.models.User User) {
        this.User = User;
    }

    /**
     *
     * @return
     * The Latitude
     */
    @JsonProperty("Latitude")
    public String getLatitude() {
        return Latitude;
    }

    /**
     *
     * @param Latitude
     * The Latitude
     */
    @JsonProperty("Latitude")
    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    /**
     *
     * @return
     * The Longitude
     */
    @JsonProperty("Longitude")
    public String getLongitude() {
        return Longitude;
    }

    /**
     *
     * @param Longitude
     * The Longitude
     */
    @JsonProperty("Longitude")
    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    /**
     *
     * @return
     * The LocationCaption
     */
    @JsonProperty("LocationCaption")
    public String getLocationCaption() {
        return LocationCaption;
    }

    /**
     *
     * @param LocationCaption
     * The LocationCaption
     */
    @JsonProperty("LocationCaption")
    public void setLocationCaption(String LocationCaption) {
        this.LocationCaption = LocationCaption;
    }

    /**
     *
     * @return
     * The Description
     */
    @JsonProperty("Description")
    public String getDescription() {
        return Description;
    }

    /**
     *
     * @param Description
     * The Description
     */
    @JsonProperty("Description")
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     *
     * @return
     * The EventDate
     */
    @JsonProperty("EventDate")
    public String getEventDate() {
        return EventDate;
    }

    /**
     *
     * @param EventDate
     * The EventDate
     */
    @JsonProperty("EventDate")
    public void setEventDate(String EventDate) {
        this.EventDate = EventDate;
    }

    /**
     *
     * @return
     * The DateCreate
     */
    @JsonProperty("DateCreate")
    public String getDateCreate() {
        return DateCreate;
    }

    /**
     *
     * @param DateCreate
     * The DateCreate
     */
    @JsonProperty("DateCreate")
    public void setDateCreate(String DateCreate) {
        this.DateCreate = DateCreate;
    }

    /**
     *
     * @return
     * The LastComments
     */
    @JsonProperty("LastComments")
    public Object getLastComments() {
        return LastComments;
    }

    /**
     *
     * @param LastComments
     * The LastComments
     */
    @JsonProperty("LastComments")
    public void setLastComments(Object LastComments) {
        this.LastComments = LastComments;
    }

    /**
     *
     * @return
     * The Photos
     */
    @JsonProperty("Photos")
    public Object getPhotos() {
        return Photos;
    }

    /**
     *
     * @param Photos
     * The Photos
     */
    @JsonProperty("Photos")
    public void setPhotos(Object Photos) {
        this.Photos = Photos;
    }

    /**
     *
     * @return
     * The LastLikes
     */
    @JsonProperty("LastLikes")
    public Object getLastLikes() {
        return LastLikes;
    }

    /**
     *
     * @param LastLikes
     * The LastLikes
     */
    @JsonProperty("LastLikes")
    public void setLastLikes(Object LastLikes) {
        this.LastLikes = LastLikes;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

