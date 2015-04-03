package com.Android.ICreated.network.requests;

import com.Android.ICreated.network.CurrentUser;
import com.Android.ICreated.network.ServerURLs;
import com.Android.ICreated.network.responses.models.SignInModel;
import com.octo.android.robospice.request.SpiceRequest;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Филипп on 03.04.2015.
 */
public class UserInfoRequest extends SpiceRequest<String>{

    public UserInfoRequest() {
        super(String.class);
    }

    @Override
    public String loadDataFromNetwork() throws Exception {

        String url = ServerURLs.SERVER_URL + ServerURLs.USER_INFO;


        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", "Bearer " + CurrentUser.getBearerToken());

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class );
        return " ";
    }
}
