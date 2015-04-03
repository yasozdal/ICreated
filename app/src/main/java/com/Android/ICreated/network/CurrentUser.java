package com.Android.ICreated.network;

/**
 * Created by Филипп on 03.04.2015.
 */
public final class CurrentUser {
    private static String bearerToken;
    private static String userName;

    public static String getUserName(){
        return userName;
    }

    public static void setUserName(String userName) {
        CurrentUser.userName = userName;
    }

    public static void setBearerToken(String bearerToken) {
        CurrentUser.bearerToken = bearerToken;
    }

    public static String getBearerToken() {
        return CurrentUser.bearerToken;
    }
}
