package com.example.localdogs.data.awsinterface.api;

import com.example.localdogs.data.User;
import com.example.localdogs.data.awsinterface.RequestResult;

public class ProfileResult extends RequestResult {

    protected ProfileResult(User user, String message) {
        super(user, message);
    }

    protected ProfileResult(byte[] user, String message) {
        super(user, message);
    }

}
