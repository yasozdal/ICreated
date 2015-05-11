package com.Android.ICreated.network.responses;

import com.Android.ICreated.network.responses.models.User;
import com.Android.ICreated.network.responses.models.UserInfoModel;

/**
 * Created by Филипп on 15.04.2015.
 */
public class UserInfoResponse extends Response<UserInfoModel>{
    public UserInfoResponse(Integer statusCode, UserInfoModel response) {
            super(statusCode, response);
        }
}
