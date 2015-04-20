package com.Android.ICreated.network.responses;

/**
 * Created by Филипп on 02.04.2015.
 */
public class SignInResponse extends Response<String>
{
    public SignInResponse(Integer statusCode, String response) {
        super(statusCode, response);
    }
}