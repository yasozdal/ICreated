package com.Android.ICreated;

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

    HttpClient httpClient;
    String serverURL;
    String registration;

    ServerAPI()
    {
        httpClient = new DefaultHttpClient();
        serverURL = "http://customer87-001-site1.myasp.net/";
        registration = "api/Account/Register";
    }

    public String RegistrationNewUser(String userName, String password, String confirmPassword)
    {
        try
        {
            HttpPost request = new HttpPost(serverURL);

            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

            urlParameters.add(new BasicNameValuePair("UserName", userName));
            urlParameters.add(new BasicNameValuePair("UserName", password));
            urlParameters.add(new BasicNameValuePair("UserName", confirmPassword));

            request.setEntity(new UrlEncodedFormEntity(urlParameters));

            request.addHeader("content-type", "application/json");

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
            httpClient.getConnectionManager().shutdown();
        }

    }


}
