package com.example.localdogs.data.awsinterface;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.localdogs.data.awsinterface.RequestQueueSingleton;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class RequestsOLD {

    private String url = "https://njxnl2knh4.execute-api.us-east-2.amazonaws.com/LocalDogsAPI";
    private Context context;

    public RequestsOLD(Context context){
        this.context = context;
    }
    public void postRequest(String uri, JSONObject data, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener){
        // asynchronous call
        JsonObjectRequest jsonReq = new JsonObjectRequest
                (
                        Request.Method.POST,
                        this.url + "/user/create",
                        data,
                        successListener,
                        errorListener
                );
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonReq);
    }
    public JSONObject postRequest(String uri, JSONObject data){
        // synchronous call
        JSONObject response = null;
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonReq = new JsonObjectRequest
                (
                        Request.Method.POST,
                        this.url + "/user/create",
                        data,
                        future,
                        future
                );
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonReq);
        // handle errors responses
        try {
            response = future.get();
            System.out.println(response);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("fail1");
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("fail2");
        }
        return response;
    }
    public JSONObject getRequest(String uri, String query){
        // synchronous call
        JSONObject response = null;
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest jsonReq = new JsonObjectRequest
                (
                        Request.Method.GET,
                        this.url + uri + '?'+ query,
                        null,
                        future,
                        future
                );
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonReq);
        // handle errors responses
        try{
            response = future.get();
            System.out.println(response);
        }catch(InterruptedException e){
            e.printStackTrace();
            System.out.println("fail1");
        }catch(ExecutionException e){
            e.printStackTrace();
            System.out.println("fail2");
        }

        return response;
    }
    public void getRequest(String uri, String query, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener){
        // asynchronous call
        JsonObjectRequest jsonReq = new JsonObjectRequest
                (
                        Request.Method.GET,
                        this.url + uri + '?'+ query,
                        null,
                        successListener,
                        errorListener
                );
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonReq);
    }
}
