package com.Android.ICreated.network;

import com.Android.ICreated.network.requests.*;
import com.Android.ICreated.network.responses.AddEventResponse;
import com.octo.android.robospice.request.SpiceRequest;

import java.util.Calendar;

/**
 * Created by Филипп on 11.05.2015.
 */
public interface RequestsFactory {
    AddEventRequest addEventRequest(String Latitude, String Longitude, String Desctiption, Calendar DateTime);
    GetEventsRequest getEventsRequest();
    RegisterRequest registerRequest(String name, String password, String passwordConfirm);
    SignInRequest signInRequest(String name, String password);
    UserInfoRequest userInfoRequest();
}
