package com.Android.ICreated.network.requests;

import com.Android.ICreated.network.Network;
import com.Android.ICreated.network.responses.GetEventsResponse;
import com.Android.ICreated.network.responses.models.EventModel;
import com.octo.android.robospice.request.SpiceRequest;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


/**
 * Created by Филипп on 20.04.2015.
 */
public class GetEventsRequest extends SpiceRequest<GetEventsResponse>{


    public GetEventsRequest() {
        super(GetEventsResponse.class);
    }

    @Override
    public GetEventsResponse loadDataFromNetwork() throws Exception {

        String url = Network.SERVER_URL + Network.EVENTS + "?count=50";


        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());


        try {
            ResponseEntity<EventModel[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, EventModel[].class);
            return new GetEventsResponse(responseEntity.getStatusCode().value(), responseEntity.getBody());
        }
        catch (HttpClientErrorException e) {
            return new GetEventsResponse(e.getStatusCode().value(), null);
        }

    }

}
