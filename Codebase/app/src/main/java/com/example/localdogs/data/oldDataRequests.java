package com.example.localdogs.data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class oldDataRequests{

    private String baseApiEndpoint;
    private GsonBuilder gsonBuilder;
    private Gson gson;

    public oldDataRequests(){
        this.baseApiEndpoint = "https://njxnl2knh4.execute-api.us-east-2.amazonaws.com/beeta";
        this.gsonBuilder = new GsonBuilder();
        this.gson = new Gson();
    }

    public String retrieveUserInfo(String param, String key){
        String query = param + "=" + key;
        return this.__makeGetRequest("/user/retrieve?" + query);
    }

    public String sendNewUserInfo(String userInfo){
        return this.__makePutRequest("/user/create", userInfo);
    }

    // should make __makeGetRequest and __MakePostRequest and __MakeDeleteRequest and __MakeUpdateRequest
    private String __makeGetRequest(String endPoint){
        URL url;
        HttpsURLConnection conn;
        String response = "";
        try {
            url = new URL(this.baseApiEndpoint + endPoint);
            try {
               conn = (HttpsURLConnection) url.openConnection();
               conn.setRequestMethod("GET");
               /* only need to specify content-type with PUT/POST */
               //conn.setRequestProperty("", key);

               if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                   BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                   try{
                       response = this.__readHttpsInputStream(buffer);
                       //System.out.println(response);
                   }catch(IOException e) {
                       e.printStackTrace();
                       System.err.println("DataRequests.readHttpsInputStream: error reading InputStream");
                   }
               }
               else{
                   System.out.println("failed to establish a connection");
               }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("DataRequests.MakeRequests: HttpsURLConnection creation failed.");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.err.println("DataRequests.MakeRequests: URL object creation failed.");
        }
        return response;
    }

    private String __makePutRequest(String endPoint, String data){
        System.out.println(data);
        URL url;
        HttpsURLConnection conn;
        String response = "";
        try {
            url = new URL(this.baseApiEndpoint + endPoint);
            try {
                conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(data);
                if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    try{
                        response = this.__readHttpsInputStream(buffer);
                        //System.out.println(response);
                    }catch(IOException e) {
                        e.printStackTrace();
                        System.err.println("DataRequests.readHttpsInputStream: error reading InputStream");
                    }
                }
                else{
                    System.out.println("failed to establish a connection");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("DataRequests.MakeRequests: HttpsURLConnection creation failed.");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.err.println("DataRequests.MakeRequests: URL object creation failed.");
        }
        return response;
    }

    private String __readHttpsInputStream(BufferedReader buffer) throws IOException {
        StringBuilder response = new StringBuilder();
        String readLine;
        try{
            while((readLine = buffer.readLine()) != null){
                response.append(readLine);
            }
            buffer.close();
        }catch(IOException e){
            e.printStackTrace();
            throw e;
        }
        return response.toString();
    }

}
