package com.example.localdogs.data.awsinterface;

import com.example.localdogs.data.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public abstract class RequestResult {
    private User userProfile;
    private String message;

    protected RequestResult(User user, String message){
        userProfile = user;
        this.message = message;
    }

    protected RequestResult(byte[] user, String message){
        try {
            userProfile = User.toUser(new JSONObject(new String(user, "UTF-8")));
        } catch (UnsupportedEncodingException | JSONException e) {
            // both of these should never happen
            // JSONException would be a case where the backend is spitting out garbage for some
            // reason
            // UnsupportedEncodingException will only happen if something is tragically wrong
            // with the JVM on the android device
            e.printStackTrace();
        }
        this.message = message;
    }

    public User getUserProfile(){
        return userProfile;
    }

    public String getMessage(){
        return message;
    }

}
