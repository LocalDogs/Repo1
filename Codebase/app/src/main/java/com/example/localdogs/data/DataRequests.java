package com.example.localdogs.data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class DataRequests{

    private String baseApiEndpoint;
    private GsonBuilder gsonBuilder;
    private Gson gson;

    public DataRequests(){
        this.baseApiEndpoint = "https://njxnl2knh4.execute-api.us-east-2.amazonaws.com/beeta";
        this.gsonBuilder = new GsonBuilder();
        this.gson = new Gson();
    }

    public void RetrieveUserProfile(String param, String key){
        String query = param + "=" + key;
        this.__MakeGetRequest("/user/retrieve?" + query);
    }
    // should make __MakeGetRequest and __MakePostRequest and __MakeDeleteRequest and __MakeUpdateRequest
    private void __MakeGetRequest(String endPoint){

        URL url;
        HttpURLConnection conn;
        try {
            url = new URL(this.baseApiEndpoint + endPoint);
            try {
               conn = (HttpURLConnection) url.openConnection();
               conn.setRequestMethod("GET");
               /* only need to specify content-type with PUT/POST */
               //conn.setRequestProperty("", key);
               if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                   BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                   try{
                       String response = this.__readHttpInputStream(buffer);
                       System.out.println(response);
                       /* perform gson transformation here... */
                       // maybe make helper fxn
                       GsonBuilder gsonBuilder = new GsonBuilder();
                       gsonBuilder.setPrettyPrinting();
                       Gson gson = gsonBuilder.create();
                       User[] users = gson.fromJson(response, User[].class);
                       for (User user : users){
                           System.out.println(user);
                       }
                   }catch(IOException e) {
                       e.printStackTrace();
                       System.err.println("DataRequests.readHttpInputStream: error reading InputStream");
                   }
               }
               else{
                   System.out.println("failed to establish a connection");
               }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("DataRequests.MakeRequests: HttpURLConnection creation failed.");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.err.println("DataRequests.MakeRequests: URL object creation failed.");
        }
    }

    private String __readHttpInputStream(BufferedReader buffer) throws IOException {
        StringBuilder response = new StringBuilder();
        String readLine;
        try{
            while((readLine = buffer.readLine()) != null){
                response.append(readLine);
            }
        }catch(IOException e){
            e.printStackTrace();
            throw e;
        }
        return response.toString();
    }

}
