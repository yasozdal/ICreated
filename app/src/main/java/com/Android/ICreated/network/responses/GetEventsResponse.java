package com.Android.ICreated.network.responses;

import com.Android.ICreated.network.responses.models.EventModel;

/**
 * Created by Филипп on 20.04.2015.
 */
public class GetEventsResponse extends Response<EventModel[]> {
    public GetEventsResponse(Integer statusCode, EventModel[] response) {
        super(statusCode, response);
    }
}
