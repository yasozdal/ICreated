package com.Android.ICreated;

import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
        protected JSONArray resultArray;
        public String type;
        Response(){}
        Response(HttpResponse response){
            this.response = response;
            type = response.getStatusLine().getReasonPhrase();
            getResult();
        }
        protected void getResult() {
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(this.response.getEntity().getContent()));
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                try {
                    this.result = new JSONObject(result.toString());
                } catch (JSONException e){
                    this.resultArray = new JSONArray(result.toString());
                }
                Log.d("Response", result.toString());
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
    private static final HttpParams params;
    private static final HttpClient httpClient;
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

    static{
        params = new BasicHttpParams();
        HttpProtocolParams.setContentCharset(params, "utf-8");
        httpClient = new DefaultHttpClient();

    }

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
                Log.d("Token doInBackground params", params.toString());
                request.setEntity(new UrlEncodedFormEntity(params));
                request.addHeader("Content-Type", "application/x-www-form-urlencoded");

                HttpResponse response = httpClient.execute(request);
                Log.d("Token doInB response", response.getStatusLine().getReasonPhrase());
                //return new Response(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
                return new Token(response);

            }
            catch (Exception ex)
            {
                Log.d("Token doInBackground", ex.toString());
                return new Token();
            }
        }
    }

    private static class addEvent extends AsyncTask<String, String, Response>
    {

        protected Response doInBackground(String... event) // костыль, разобраться как запустить без аргументов, и пофиксить
        {   try {
            HttpPost request = new HttpPost(serverURL + events);


            JSONObject userInJSON = new JSONObject();

            userInJSON.put("Latitude", event[0]);
            userInJSON.put("Longitude", event[1]);
            userInJSON.put("Description", event[2]);
            userInJSON.put("EventDate", event[3]);
            Log.d("JSON", userInJSON.toString());
            request.setEntity(new ByteArrayEntity(userInJSON.toString().getBytes("UTF8")));
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", "Bearer " + user.getToken());

            HttpResponse response = httpClient.execute(request);


            return new Response(response);

        }
        catch (Exception ex)
        {
            Log.d("addEvent class", ex.getMessage());
            return new Response();
        }
        }
    }

    private static class getEvents extends AsyncTask<String, String, Response>
    {

        protected Response doInBackground(String... doesntmatter) // костыль, разобраться как запустить без аргументов, и пофиксить
        {   try {
            HttpGet request = new HttpGet(serverURL + events);

//            JSONObject userInJSON = new JSONObject();
//
            request.addHeader("Content-Type", "application/json");
            Log.d("Token when getEvents", user.getToken());
            request.addHeader("Authorization", "Bearer " + user.getToken());
            HttpResponse response = httpClient.execute(request);
            Log.d("getEvBackground", response.getStatusLine().getReasonPhrase());

            return new Response(response);

        }
        catch (Exception ex)
        {
            Log.d("addEvent class", ex.getMessage());
            return new Response();
        }
        }
    }


    public static void setUser(String name, String password){
        user = new User(name, password);
    }
    public static void setUser(String name, String password, String confirmPassword){ user = new User(name, password, confirmPassword); }

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

    public static boolean addEvent(String Latitude, String Longitude, String Description, String EventDate) {
        try {
            return ((new addEvent().execute(Latitude, Longitude, Description, "05.10.2014 21:44:36").get()).type).equals("Created");
        } catch (Exception e) {
            Log.d("addEvent", e.toString());
            return false;
        }
    }

    public static Calendar convertStringDateToCalendar(String strDate) {
        Calendar cal = null;
        Log.d("convertStrDTC", strDate);
        if (strDate != null) {
            try {
                strDate = strDate.replace("T", " ");
                DateFormat formatter = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");
                Date date = formatter.parse(strDate);
                cal = Calendar.getInstance();
                cal.setTime(date);
            } catch (Exception e) {
                Log.d("convertStrDTC", e.toString());
            }
        }

        return cal;
    }
    private static ArrayList<Event> createEvents(JSONArray eventsInJSONE){

            ArrayList<Event> events = new ArrayList<Event>();
            JSONObject eventInJSONE;
            String description;
            String title; // пока будет то же самое что и decription, ибо с сервера приходит только description
            double latitude;
            double longitude;
            Calendar date;
            Event event;

        try {
            for(int i = 0; i < eventsInJSONE.length(); ++i){
                eventInJSONE = eventsInJSONE.getJSONObject(i);
                description = eventInJSONE.getString("Description");
                title = description;
                latitude = eventInJSONE.getDouble("Latitude");
                longitude = eventInJSONE.getDouble("Longitude");
                date =  convertStringDateToCalendar(eventInJSONE.getString("EventDate"));
//                events.add(new Event(date, new LatLng(latitude, longitude), description, description));
                event = new Event(date, new LatLng(latitude, longitude), description, description);
                events.add(event);
            }
            return events;
        } catch (JSONException e){
            Log.d("createEvent Ex ", e.toString());
        }
        return null;
    }
    public static ArrayList<Event> getEvents(){
        try {
            Response test = new getEvents().execute().get();

            Log.d("getEvents", test.resultArray.toString());
     //       createEvents(test.resultArray);
            return createEvents(test.resultArray);
        } catch (Exception e){
            Log.d("getEvents", e.toString());
            return null;
        }
    }
}
