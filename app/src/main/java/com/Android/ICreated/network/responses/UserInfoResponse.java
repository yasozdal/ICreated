package com.Android.ICreated.network.responses;

import com.Android.ICreated.network.responses.models.UserInfoModel;

/**
 * Created by ������ on 15.04.2015.
 */
public class UserInfoResponse extends Response{
    public UserInfoResponse(Integer statusCode, UserInfoModel response) {
            super(statusCode, response);
        }
}
