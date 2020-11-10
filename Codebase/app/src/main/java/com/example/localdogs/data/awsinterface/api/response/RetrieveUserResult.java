package com.example.localdogs.data.awsinterface.api.response;

import android.util.Log;

import com.example.localdogs.data.User;

import org.json.JSONException;
import org.json.JSONObject;

public class RetrieveUserResult extends UserResult{

    private boolean isFound;
    private String query;

    public RetrieveUserResult(byte[] rawResponse) {
        super(rawResponse);
        isFound = false;
        query = "";
        try{
            isFound = getResponseAsJSONObject().getBoolean("found");
            query = getResponseAsJSONObject().getString("query");
        }catch(JSONException e){
            Log.e("RetrieveUserResult", e.getMessage());
        }
    }

    public RetrieveUserResult(String message, String error){
        super(message, error);
    }

    @Override
    public boolean isSuccess() {
        return isFound;
    }

    public boolean getIsFound(){
        return isFound;
    }

    public String getQuery(){
        return query;
    }
}
