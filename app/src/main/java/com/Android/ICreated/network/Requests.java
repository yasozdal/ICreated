package com.Android.ICreated.network;

import com.Android.ICreated.network.requests.*;

import java.util.Calendar;

/**
 * Created by Филипп on 11.05.2015.
 */
public final class Requests implements RequestsFactory {
    @Override
    public AddEventRequest addEventRequest(String Latitude, String Longitude, String Desctiption, Calendar DateTime) {
        return new AddEventRequest(Latitude, Longitude, Desctiption, DateTime);
    }
    @Override
    public GetEventsRequest getEventsRequest() {
        return new GetEventsRequest();
    }
    @Override
    public RegisterRequest registerRequest(String name, String password, String passwordConfirm) {
        return new RegisterRequest(name, password, passwordConfirm);
    }
    @Override
    public SignInRequest signInRequest(String name, String password) {
        return new SignInRequest(name, password);
    }
    @Override
    public UserInfoRequest userInfoRequest() {
        return new UserInfoRequest();
    }
}
