package com.Android.ICreated.network;

import android.net.Uri;
import android.util.Log;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import com.octo.android.robospice.request.okhttp.OkHttpSpiceRequest;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONObject;

/**
 * Created by Филипп on 22.03.2015.
 */
public class RegisterRequest extends OkHttpSpiceRequest<String>{

    private String name;
    private String password;
    private String passwordConfirm;

    public RegisterRequest(String name, String password, String passwordConfirm){
        super(String.class);
        this.name = name;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public String loadDataFromNetwork() throws Exception {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setContentCharset(params, "utf-8");
        HttpClient httpClient = new DefaultHttpClient();

        HttpPost request = new HttpPost("http://icreate.azurewebsites.net/" + "api/Account/Register");

        JSONObject userInJSON = new JSONObject();

        userInJSON.put("UserName", name);
        userInJSON.put("Password", password);
        userInJSON.put("ConfirmPassword", passwordConfirm);

        request.setEntity(new StringEntity(userInJSON.toString()));
        request.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(request);
        return Boolean.toString(response.getStatusLine().getReasonPhrase().equals("OK"));
    }
}
