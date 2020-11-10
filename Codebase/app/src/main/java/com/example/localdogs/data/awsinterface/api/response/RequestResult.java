package com.example.localdogs.data.awsinterface.api.response;

import android.util.Log;

import com.example.localdogs.data.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public abstract class RequestResult {
    private JSONObject responseAsJSONObject;
    private String message;
    private String error;
    private byte[] rawBytes;

    public RequestResult(byte[] rawResponse){
        rawBytes = rawResponse;
        try {
            responseAsJSONObject = new JSONObject(new String(rawResponse, "UTF-8"));
            this.message = responseAsJSONObject.getString("message");
            this.error = responseAsJSONObject.getString("error");
        } catch (UnsupportedEncodingException | JSONException e) {
            // both of these should never happen
            // JSONException would be a case where the backend is spitting out garbage for some
            // reason
            // UnsupportedEncodingException will only happen if something is tragically wrong
            // with the JVM on the android device
            this.message = "";
            this.error = e.getMessage();
            responseAsJSONObject = null;
            Log.e("RequestResult", e.getMessage());
        }
    }

    public RequestResult (String message, String error){
        responseAsJSONObject = null;
        this.message = message;
        this.error = error;
    }

    public JSONObject getResponseAsJSONObject(){
        return responseAsJSONObject;
    }

    public String getMessage(){
        return message;
    }

    public String getError(){
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(String error) {
        this.error = error;
    }

    public abstract boolean isSuccess();

    public byte[] getRawBytes(){
        return rawBytes;
    }

}
