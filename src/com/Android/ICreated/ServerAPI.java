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
import org.json.JSONObject;

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

    private class RegistrationNewUser extends AsyncTask<String, String, String>
    {
        protected String doInBackground(String... user)
            {
                try
                {
                    HttpPost request = new HttpPost(serverURL + registration);
//                    List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

//                    urlParameters.add(new BasicNameValuePair("UserName:", user[0]));
//                    urlParameters.add(new BasicNameValuePair("Password:", user[1]));
//                    urlParameters.add(new BasicNameValuePair("ConfirmPassword:", user[2]));
//                    p.setEntity(new StringEntity("{\"username\":\"" + this.apiusername + "\",\"password\":\"" + this.apipassword + "\"}",
//                            ContentType.create("application/json")));
                    request.setEntity(new StringEntity("{\"UserName\":\"" + user[0] + "\",\"Password\":\"" + user[1] + "\",\"ConfirmPassword\":\"" +
                            user[2] + "\"}"));

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
                    httpClient.getConnectionManager().shutdown();
                }
        }
    }

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
            return (new RegistrationNewUser().execute(userName, password, confirmPassword)).get();
        }
        catch (Exception e)
        {
            return e.toString();
        }
    }

}
