package com.Android.ICreated.network.requests;

import android.os.Message;
import android.util.Log;
import com.Android.ICreated.network.ServerURLs;
import com.Android.ICreated.network.responses.models.SignInModel;
import com.octo.android.robospice.request.SpiceRequest;
import org.apache.http.client.methods.HttpHead;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Филипп on 02.04.2015.
 */
public class SignInRequest extends SpiceRequest<String> {

    String name;
    String password;

    public SignInRequest(String name, String password) {
        super(String.class);
        this.name = name;
        this.password = password;
    }

    @Override
    public String loadDataFromNetwork() throws Exception {

        String url = ServerURLs.SERVER_URL + ServerURLs.TOKEN;

        String requestBody = String.format("grant_type=password&username=%s&password=%s", name, password);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(requestBody, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        SignInModel response = restTemplate.postForObject(url, entity, SignInModel.class);

        return " ";
    }
}
