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

    public RequestResult(byte[] rawResponse){
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
            e.printStackTrace();
        }

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

}
