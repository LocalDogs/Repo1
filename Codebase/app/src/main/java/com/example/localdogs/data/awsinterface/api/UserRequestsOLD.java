package com.example.localdogs.data.awsinterface.api;
import android.content.Context;

import com.android.volley.Response;
import com.example.localdogs.data.User;
import com.example.localdogs.data.awsinterface.api.RequestsOLD;

import org.json.JSONObject;

public class UserRequestsOLD extends RequestsOLD {

    private String url = "https://njxnl2knh4.execute-api.us-east-2.amazonaws.com/beeta";
    private Context context;

    public UserRequestsOLD(Context context){
        super(context);
        this.context = context;
    }
    public void uploadNewUser(User user, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener){
        // asynchronous call
        super.postRequest("/user/create", user.toJSONObject(), successListener, errorListener);
    }
    public JSONObject uploadNewUser(User user){
        // asynchronous call
        return super.postRequest("/user/create", user.toJSONObject());
    }
    public void retrieveUserProfile(String email, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener){
        String query = "email=" + email;
        super.getRequest("/user/retrieve", query, successListener, errorListener);
    }

    public User retrieveUserProfile(String email){
        String query = "email=" + email;
        JSONObject jsonUser = super.getRequest("/user/retrieve", query);
        return User.toUser(jsonUser);
    }

}
