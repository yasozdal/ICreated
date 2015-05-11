package com.Android.ICreated.network;

import java.util.Collection;

/**
 * Created by Филипп on 03.04.2015.
 */
public final class Network {
    public static final String SERVER_URL = "http://icreate.azurewebsites.net/";
    public static final String TOKEN = "Token";
    public static final String REGISTER = "api/Account/Register";
    public static final String USER_INFO = "/api/Account/UserInfo";
    public static final String EVENTS = "api/Events";
    public static final String EVENT_COMMENTS = "api/eventComments";
    public static final String FOLLOW = "api/friends/follow";
    public static final String UNFOLLOW = "api/friends/unfollow";

    public static RequestsFactory requestsFactory = new Requests();

    public static void setRequestsFactory(RequestsFactory factory) {
        requestsFactory = factory;
    }

    public static RequestsFactory getRequestsFactory() {
        return requestsFactory;
    }

}
