package com.Android.ICreated.network.responses;

/**
 * Created by Филипп on 02.04.2015.
 */
public class Response<T> {

    public Response(String statusCode, T response){
        this.statusCode = statusCode;
        this.response = response;
    }
    private String statusCode;
    private T response;

    public T getResponse() {
        return response;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
