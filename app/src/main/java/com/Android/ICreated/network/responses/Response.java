package com.Android.ICreated.network.responses;

/**
 * Created by Филипп on 02.04.2015.
 */
public class Response<T> {

    public Response(Integer statusCode, T response){
        this.statusCode = statusCode;
        this.response = response;
    }
    private Integer statusCode;
    private T response;

    public T getResponse() {
        return response;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
