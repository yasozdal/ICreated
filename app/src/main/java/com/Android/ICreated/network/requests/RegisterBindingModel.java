package com.Android.ICreated.network.requests;

/**
 * Created by Филипп on 03.04.2015.
 */
public class RegisterBindingModel {
    private String UserName;

    private String Password;

    private String ConfirmPassword;

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.ConfirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }
}
