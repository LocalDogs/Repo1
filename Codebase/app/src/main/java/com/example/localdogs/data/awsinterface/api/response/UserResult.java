package com.example.localdogs.data.awsinterface.api.response;

import android.util.Log;

import com.example.localdogs.data.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserResult extends RequestResult{
    User user;

    public UserResult(byte[] rawResponse) {
        super(rawResponse);
        user = null;
        try {
            JSONObject jsonUser = getResponseAsJSONObject().getJSONObject("user");
            if(jsonUser != null) user = User.toUser(getResponseAsJSONObject().getJSONObject("user"));
        } catch (JSONException e) {
            Log.e("UserResult", e.getMessage());
        }
    }

    public UserResult(String message, String error) {
        super(message, error);
        user = null;
    }

    @Override
    public boolean isSuccess() {
        if(user == null) return false;
        return true;
    }

    public User getUser(){
        return user;
    }
}
