package com.example.localdogs.data.awsinterface;

import com.example.localdogs.data.User;

public class SignInResult extends RequestResult{

    private User userProfile;
    private String message;

    protected SignInResult(User user, String message){
        super(user, message);
    }

    protected SignInResult(byte[] user, String message){
        super(user, message);
    }

}
