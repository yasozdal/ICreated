package com.Android.ICreated.network.requests;

import com.Android.ICreated.network.ServerURLs;

import com.Android.ICreated.network.requests.models.RegisterBindingModel;
import com.Android.ICreated.network.responses.RegisterResponse;
import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Филипп on 22.03.2015.
 */
public class RegisterRequest extends OkHttpSpiceRequest<RegisterResponse>{

    private String name;
    private String password;
    private String confirmPassword;

    public RegisterRequest(String name, String password, String passwordConfirm){
        super(RegisterResponse.class);
        this.name = name;
        this.password = password;
        this.confirmPassword = passwordConfirm;
    }

    @Override
    public RegisterResponse loadDataFromNetwork() throws Exception {

        name = "skdjssasasq";
        password = "sakdlaskasddasd";
        confirmPassword = password;

        RegisterBindingModel registerBindingModel = new RegisterBindingModel();
        registerBindingModel.setUserName(name);
        registerBindingModel.setPassword(password);
        registerBindingModel.setConfirmPassword(confirmPassword);
        
        String url = ServerURLs.SERVER_URL + ServerURLs.REGISTER;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterBindingModel> requestEntity = new HttpEntity<RegisterBindingModel>(registerBindingModel, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String response = null;

        String statusCode = null;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            statusCode = responseEntity.getStatusCode().toString();
            response = responseEntity.getBody();
        }
        catch (HttpClientErrorException e) {
            statusCode = e.getStatusCode().toString();
            response = e.getResponseBodyAsString();
        }

        return new RegisterResponse(statusCode, response);
    }
}
