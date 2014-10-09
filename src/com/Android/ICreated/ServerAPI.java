package com.Android.ICreated;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Филипп on 08.10.2014.
 */
public class ServerAPI {

    private static final HttpClient httpClient = new DefaultHttpClient();
    private static final String serverURL = "http://customer87-001-site1.myasp.net/";
    private static final String registration = "api/Account/Register";
    private static final String token = "Token";
    private static final String events = "api/Events";
    private static final String eventsComment = "api/eventComments";
    private static final String follow = "";
    private static final String unfollow = "";
    private static final String friendList = "";

    private class RegistrationNewUser extends AsyncTask<String, String, String>
    {
        protected String doInBackground(String... user)
            {
                try
                {
                    HttpPost request = new HttpPost(serverURL + registration);

                    request.setEntity(new StringEntity(
                            "{" +
                                    "\"UserName\":\"" + user[0] + "\"," +
                                    "\"Password\":\"" + user[1] + "\",\"" +
                                    "ConfirmPassword\":\"" + user[2] +
                            "\"}"));

                    request.addHeader("Content-Type", "application/json");

                    HttpResponse response = httpClient.execute(request);

                    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    StringBuffer result = new StringBuffer();
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }

                    return result.toString();

                }
                catch (Exception ex)
                {
                    return "Exception " + ex.toString();
                }
                finally
                {
//                    httpClient.getConnectionManager().shutdown();
                }
        }
    }

    private class getToken extends AsyncTask<String, String, String>
    {
        protected String doInBackground(String... user)
        {
            try
            {
                HttpPost request = new HttpPost(serverURL + token);

//                request.setEntity(new StringEntity(
//
//                                "grant_type=password&username=" + user[0] +
//                                "&password=" + user[1]
//                        ));
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("grant_type", "password"));
                params.add(new BasicNameValuePair("username", user[0]));
                params.add(new BasicNameValuePair("password", user[1]));

                request.setEntity(new UrlEncodedFormEntity(params));
                request.addHeader("Content-Type", "application/x-www-form-urlencoded");

                HttpResponse response = httpClient.execute(request);

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                return result.toString();

            }
            catch (Exception ex)
            {
                return "Exception " + ex.toString();
            }
            finally
            {
//                httpClient.getConnectionManager().shutdown();
            }
        }
    }

    // доделать регистрацию!
    public String RegistrationNewUser(String userName, String password, String confirmPassword)
    {
        try
        {
            return (new RegistrationNewUser().execute(userName, password, confirmPassword)).get();
        }
        catch (Exception e)
        {
            return e.toString();
        }
    }

    public String getToken(String userName, String password)
    {
        try
        {
            return (new getToken().execute(userName, password)).get();
        }
        catch (Exception e)
        {
            return e.toString();
        }
    }

}
