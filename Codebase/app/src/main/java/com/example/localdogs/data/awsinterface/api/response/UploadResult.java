package com.example.localdogs.data.awsinterface.api.response;

import android.util.Log;

import org.json.JSONException;

public class UploadResult extends RetrieveUserResult{

    private boolean isInserted;
    private boolean isDuplicate;
    private String payLoadId;

    public UploadResult(byte[] rawResponse) {
        super(rawResponse);
        isInserted = false;
        isDuplicate = false;
        payLoadId = "";
        try{
            isInserted = getResponseAsJSONObject().getBoolean("inserted");
            isDuplicate = getResponseAsJSONObject().getBoolean("duplicate");
            payLoadId = getResponseAsJSONObject().getString("payloadid");
        }catch(JSONException e){
            Log.e("UploadResult", e.getMessage());
        }
    }

    public UploadResult(String message, String error){
        super(message, error);
        isInserted = false;
        isDuplicate = false;
    }

    @Override
    public boolean isSuccess() {
        return isInserted;
    }
    public boolean isInserted(){
        return isInserted;
    }
    public boolean isDuplicate(){
        return isDuplicate;
    }

    public String getPayLoadId(){
        return payLoadId;
    }
}
