package com.Android.ICreated.network.requests;

import com.Android.ICreated.network.Converter;
import com.Android.ICreated.network.CurrentUser;
import com.Android.ICreated.network.ServerURLs;
import com.Android.ICreated.network.requests.models.AddEventBindingModel;
import com.Android.ICreated.network.requests.models.RegisterBindingModel;
import com.Android.ICreated.network.responses.AddEventResponse;
import com.Android.ICreated.network.responses.RegisterResponse;
import com.Android.ICreated.network.responses.UserInfoResponse;
import com.Android.ICreated.network.responses.models.UserInfoModel;
import com.octo.android.robospice.request.SpiceRequest;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Collections;

/**
 * Created by Филипп on 19.04.2015.
 */
public class AddEventRequest extends SpiceRequest<AddEventResponse> {

    private AddEventBindingModel AddEventBindingModel;

    public AddEventRequest(String Latitude, String Longitude, String Desctiption, Calendar DateTime) {
        super(AddEventResponse.class);
        this.AddEventBindingModel = new AddEventBindingModel();
        this.AddEventBindingModel.setLatitude(Latitude);
        this.AddEventBindingModel.setLongitude(Longitude);
        this.AddEventBindingModel.setDescription(Desctiption);
        this.AddEventBindingModel.setEventDate(Converter.CalendarToString(DateTime));
    }

    @Override
    public AddEventResponse loadDataFromNetwork() throws Exception {

        String url = ServerURLs.SERVER_URL + ServerURLs.EVENTS;


        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", "Bearer " + CurrentUser.getBearerToken());

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AddEventBindingModel> requestEntity = new HttpEntity<>(AddEventBindingModel, httpHeaders);

        String test = requestEntity.toString();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        String response = null;
        Integer statusCode = null;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            statusCode = responseEntity.getStatusCode().value();
            response = responseEntity.getBody();
        }
        catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode().value();
            response = e.getResponseBodyAsString();
        }
//        catch (HttpServerErrorException ee) {
//            String a = ee.getResponseBodyAsString();
//            Exception eee = ee;
//        }

        return new AddEventResponse(statusCode, response);
    }
}