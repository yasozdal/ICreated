package com.Android.ICreated;

import android.os.AsyncTask;
import android.util.Log;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Филипп on 08.10.2014.
 */

public class ServerAPI {

    public static class User{ // потом сделать private
        private static String name;
        private String password;
        private String confirmPassword;
        public static String token;
        User(String name, String password){
            this.name = name;
            this.password = password;
        }
        User(String name, String password, String confirmPassword){
            this.name = name;
            this.password = password;
            this.confirmPassword = confirmPassword;
        }
        public void setToken(String token){
            this.token = token;
        }
        public String getToken(){
            return this.token;
        }
        public String getName(){
            return this.name;
        }
    }

    public static class Response{

        protected HttpResponse response;
        protected JSONObject result;
        public String type;
        Response(){}
        Response(HttpResponse response){
            this.response = response;
            getResult();
        }
        protected void getResult() {
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(this.response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                    this.result = new JSONObject(result.toString());
                }
                type = response.getStatusLine().getReasonPhrase();
                Log.d("Response", type);
            } catch (Exception e) {
                Log.d("getResult()", e.getMessage());
            }
        }
    }

    public static class Token extends Response{
        private String token;
        public String message;
        Token(HttpResponse response){
            super(response);
            setToken();
        }
        Token(){}
        public void setToken(){
            try {
                this.token = super.result.getString("access_token");

            } catch (Exception e){
                Log.d("setToken", e.getMessage());
            }
        }
        public boolean correct(){
            Log.d("Token.correct", super.type);
            return (super.type.equals("OK"));
        }
    }

    public static User user; // потом сделать private
    private static final HttpClient httpClient = new DefaultHttpClient();
    private static final String serverURL = "http://customer87-001-site1.myasp.net/";
    private static final String registration = "api/Account/Register";
    private static final String token = "Token";
    private static final String events = "api/Events";
    private static final String eventsComment = "api/eventComments";
    private static final String follow = "";
    private static final String unfollow = "";
    private static final String friendList = "";
    private static final String correctRequest = "OK";
    private static final String badRequest = "Bad Request";

    private static class RegistrationNewUser extends AsyncTask<String, String, Response>
    {

        protected Response doInBackground(String... doesntmatter) // костыль, разобраться как запустить без аргументов, и пофиксить
            {   try {
                    HttpPost request = new HttpPost(serverURL + registration);

                    JSONObject userInJSON = new JSONObject();

                    userInJSON.put("UserName", user.name);
                    userInJSON.put("Password", user.password);
                    userInJSON.put("ConfirmPassword", user.confirmPassword);

                    request.setEntity(new StringEntity(userInJSON.toString()));
                    request.addHeader("Content-Type", "application/json");

                    HttpResponse response = httpClient.execute(request);


                    return new Response(response);

                }
                catch (Exception ex)
                {
                    Log.d("Exception", ex.getMessage());
                    return new Response();
                }
                finally
                {
//                    httpClient.getConnectionManager().shutdown();
                }
        }
    }

    private static class getToken extends AsyncTask<String, String, Token>
    {
        protected Token doInBackground(String... user)
        {
            try
            {
                HttpPost request = new HttpPost(serverURL + token);

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("grant_type", "password"));
                params.add(new BasicNameValuePair("username", user[0]));
                params.add(new BasicNameValuePair("password", user[1]));

                request.setEntity(new UrlEncodedFormEntity(params));
                request.addHeader("Content-Type", "application/x-www-form-urlencoded");

                HttpResponse response = httpClient.execute(request);

                //return new Response(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
                return new Token(response);

            }
            catch (Exception ex)
            {
                Log.d("Token doInBackground", ex.toString());
                return new Token();
            }
            finally
            {
//                httpClient.getConnectionManager().shutdown();
            }
        }
    }


    public static void setUser(String name, String password){
        user = new User(name, password);
    }
    public static void setUser(String name, String password, String confirmPassword){
        user = new User(name, password, confirmPassword);
    }

    public static Response RegistrationNewUser()
    {
        try
        {

            return (new RegistrationNewUser().execute()).get();
        }
        catch (Exception e)
        {
            Log.d("RegisrationNewUser", e.toString());
            return new Response();
        }
    }

    private static Token getToken()
    {
        try
        {
            return (new getToken().execute(user.name, user.password)).get();
        }
        catch (Exception e)
        {
            Log.d("getToken excp", e.getMessage());
            return new Token();
        }
    }
    public static boolean signIn(){
        try {
            Token token = getToken();
            if (token.correct()){
                user.setToken(token.token);
                return true;
            }
            else{
                user.setToken(null);
                return false;
            }


        } catch (Exception e){
            Log.d("signIn Excp", e.toString());
            return false;
        }
    }

}
