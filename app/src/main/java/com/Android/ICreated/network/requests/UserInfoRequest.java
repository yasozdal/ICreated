package com.Android.ICreated.network.requests;

import com.Android.ICreated.network.CurrentUser;
import com.Android.ICreated.network.ServerURLs;
import com.Android.ICreated.network.responses.SignInResponse;
import com.Android.ICreated.network.responses.UserInfoResponse;
import com.Android.ICreated.network.responses.models.SignInModel;
import com.Android.ICreated.network.responses.models.UserInfoModel;
import com.octo.android.robospice.request.SpiceRequest;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by Филипп on 03.04.2015.
 */
public class UserInfoRequest extends SpiceRequest<UserInfoResponse>{

    public UserInfoRequest() {
        super(UserInfoResponse.class);
    }

    @Override
    public UserInfoResponse loadDataFromNetwork() throws Exception {

        String url = ServerURLs.SERVER_URL + ServerURLs.USER_INFO;


        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", "Bearer " + CurrentUser.getBearerToken());
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        try {
            UserInfoModel response = restTemplate.postForObject(url, entity, UserInfoModel.class);

            return new UserInfoResponse(200, response);
        }
        catch (HttpClientErrorException e) {
            return new UserInfoResponse(e.getStatusCode().value(), null);
        }
    }
}
