package com.example.localdogs.data.awsinterface;

import com.example.localdogs.data.User;

public class RegisterResult extends RequestResult {

    private User userProfile;
    private String message;

    protected RegisterResult(User user, String message) {
        super(user, message);
    }

    protected RegisterResult(byte[] user, String message){
        super(user, message);
    }

}
