package com.Android.ICreated.network.requests;

import com.Android.ICreated.network.CurrentUser;
import com.Android.ICreated.network.Network;
import com.Android.ICreated.network.responses.SignInResponse;
import com.Android.ICreated.network.responses.models.SignInModel;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by Филипп on 02.04.2015.
 */
public class SignInRequest extends OkHttpSpiceRequest<SignInResponse> {

    private String name;
    private String password;

    public SignInRequest(String name, String password) {
        super(SignInResponse.class);
        this.name = name;
        this.password = password;
    }

    @Override
    public SignInResponse loadDataFromNetwork() throws Exception {

        String url = Network.SERVER_URL + Network.TOKEN;

        String requestBody = String.format("grant_type=password&username=%s&password=%s", name, password);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        try {
            SignInModel response = restTemplate.postForObject(url, entity, SignInModel.class);

            CurrentUser.setUserName(response.getUserName());
            CurrentUser.setBearerToken(response.getAccessToken());

            return new SignInResponse(200, null);
        }
        catch (HttpClientErrorException e) {

            return new SignInResponse(e.getStatusCode().value(), null);
        }


    }
}
