package com.example.localdogs.data.awsinterface.api.response;

import android.util.Log;

import com.example.localdogs.data.User;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileResult extends RequestResult {

    private boolean isFound;
    private User user;

    public ProfileResult(byte[] rawResponse) {
        super(rawResponse);
        try {
            isFound = super.getResponseAsJSONObject().getBoolean("found");
            user = User.toUser(new JSONObject(super.getResponseAsJSONObject().getString("user")));
            Log.i("ProfileResult", user.toString());
        } catch (JSONException e) {
            // plz plz no throw dis :)
            e.printStackTrace();
        }
    }

    public boolean getIsFound(){
        return isFound;
    }

    public User getUser(){
        return user;
    }

}
