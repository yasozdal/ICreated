package com.Android.ICreated.MockNetwork;

import android.util.Log;
import com.Android.ICreated.network.RequestsFactory;
import com.Android.ICreated.network.requests.*;
import com.Android.ICreated.network.responses.AddEventResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;


/**
 * Created by Филипп on 11.05.2015.
 */
public class MockRequests implements RequestsFactory{
    @Override
    public AddEventRequest addEventRequest(String Latitude, String Longitude, String Desctiption, Calendar DateTime) {
        AddEventRequest request = mock(AddEventRequest.class);
        try {
            when(request.loadDataFromNetwork()).thenReturn(new AddEventResponse(200, "OK"));
        }
        catch (Exception e) {
            Log.d("MUST NOT EXCEPTION", "Mock AddEventRequests");
        }
        return request;
    }

    @Override
    public GetEventsRequest getEventsRequest() {
        return null;
    }

    @Override
    public RegisterRequest registerRequest(String name, String password, String passwordConfirm) {
        return null;
    }

    @Override
    public SignInRequest signInRequest(String name, String password) {
        return null;
    }

    @Override
    public UserInfoRequest userInfoRequest() {
        return null;
    }
}
