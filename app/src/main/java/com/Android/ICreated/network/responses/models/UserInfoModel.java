package com.Android.ICreated.network.responses.models;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Филипп on 15.04.2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
        "UserName",
        "HasRegistered",
        "LoginProvider"
})
public class UserInfoModel {

    @JsonProperty("UserName")
    private String UserName;
    @JsonProperty("HasRegistered")
    private Boolean HasRegistered;
    @JsonProperty("LoginProvider")
    private Object LoginProvider;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * The HasRegistered
     */
    @JsonProperty("HasRegistered")
    public Boolean getHasRegistered() {
        return HasRegistered;
    }

    /**
     *
     * @param HasRegistered
     * The HasRegistered
     */
    @JsonProperty("HasRegistered")
    public void setHasRegistered(Boolean HasRegistered) {
        this.HasRegistered = HasRegistered;
    }

    /**
     *
     * @return
     * The LoginProvider
     */
    @JsonProperty("LoginProvider")
    public Object getLoginProvider() {
        return LoginProvider;
    }

    /**
     *
     * @param LoginProvider
     * The LoginProvider
     */
    @JsonProperty("LoginProvider")
    public void setLoginProvider(Object LoginProvider) {
        this.LoginProvider = LoginProvider;
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