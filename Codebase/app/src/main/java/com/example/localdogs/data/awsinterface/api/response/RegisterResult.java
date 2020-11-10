package com.example.localdogs.data.awsinterface.api.response;

import android.util.Log;

import org.json.JSONException;

public class RegisterResult extends UploadResult{

    private boolean isLoggedIn;
    private boolean isRegistered;

    public RegisterResult(byte[] rawResponse, boolean isLoggedIn, boolean isRegistered) {
        super(rawResponse);
        this.isLoggedIn = isLoggedIn;
        this.isRegistered = isRegistered;
    }

    public RegisterResult(String message, String error, boolean isLoggedIn, boolean isRegistered){
        super(message, error);
        this.isLoggedIn = isLoggedIn;
        this.isRegistered = isRegistered;
    }

    @Override
    public boolean isSuccess() {
        return (isLoggedIn && isInserted() && isRegistered);
    }

    public boolean isLoggedIn(){
        return isLoggedIn;
    }

    public boolean isRegistered(){
        return isRegistered;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
