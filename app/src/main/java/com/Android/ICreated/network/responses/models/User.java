package com.Android.ICreated.network.responses.models;

/**
 * Created by Филипп on 20.04.2015.
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
        "UserId",
        "UserName",
        "Photo"
})
public class User {

    @JsonProperty("UserId")
    private Integer UserId;
    @JsonProperty("UserName")
    private String UserName;
    @JsonProperty("Photo")
    private Object Photo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The UserId
     */
    @JsonProperty("UserId")
    public Integer getUserId() {
        return UserId;
    }

    /**
     *
     * @param UserId
     * The UserId
     */
    @JsonProperty("UserId")
    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    /**
     *
     * @return
     * The UserName
     */
    @JsonProperty("UserName")
    public String getUserName() {
        return UserName;
    }

    /**
     *
     * @param UserName
     * The UserName
     */
    @JsonProperty("UserName")
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    /**
     *
     * @return
     * The Photo
     */
    @JsonProperty("Photo")
    public Object getPhoto() {
        return Photo;
    }

    /**
     *
     * @param Photo
     * The Photo
     */
    @JsonProperty("Photo")
    public void setPhoto(Object Photo) {
        this.Photo = Photo;
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