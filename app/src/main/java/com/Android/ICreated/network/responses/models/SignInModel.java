package com.Android.ICreated.network.responses.models;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Филипп on 03.04.2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
        "access_token",
        "token_type",
        "expires_in",
        "userName",
        ".issued",
        ".expires"
})
public class SignInModel {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private long expiresIn;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty(".issued")
    private String Issued;
    @JsonProperty(".expires")
    private String Expires;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The accessToken
     */
    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param accessToken
     * The access_token
     */
    @JsonProperty("access_token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     *
     * @return
     * The tokenType
     */
    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    /**
     *
     * @param tokenType
     * The token_type
     */
    @JsonProperty("token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     *
     * @return
     * The expiresIn
     */
    @JsonProperty("expires_in")
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     *
     * @param expiresIn
     * The expires_in
     */
    @JsonProperty("expires_in")
    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     *
     * @return
     * The userName
     */
    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     * The userName
     */
    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     * The Issued
     */
    @JsonProperty(".issued")
    public String getIssued() {
        return Issued;
    }

    /**
     *
     * @param Issued
     * The .issued
     */
    @JsonProperty(".issued")
    public void setIssued(String Issued) {
        this.Issued = Issued;
    }

    /**
     *
     * @return
     * The Expires
     */
    @JsonProperty(".expires")
    public String getExpires() {
        return Expires;
    }

    /**
     *
     * @param Expires
     * The .expires
     */
    @JsonProperty(".expires")
    public void setExpires(String Expires) {
        this.Expires = Expires;
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
