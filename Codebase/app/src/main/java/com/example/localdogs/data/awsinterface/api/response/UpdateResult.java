package com.example.localdogs.data.awsinterface.api.response;

import android.util.Log;

import org.json.JSONException;

public class UpdateResult extends RequestResult{
    private boolean updated;
    private String query;

    public UpdateResult(byte[] rawResponse) {
        super(rawResponse);
        try{
            query = getResponseAsJSONObject().getString("query");
            updated = getResponseAsJSONObject().getBoolean("updated");
        }catch(JSONException e){
            Log.e("RetrieveUserResult", e.getMessage());
        }
    }

    @Override
    public boolean isSuccess() {
        return updated;
    }

    public String getQuery(){
        return query;
    }
}
