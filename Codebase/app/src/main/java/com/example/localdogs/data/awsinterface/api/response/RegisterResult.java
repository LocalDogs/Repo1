package com.example.localdogs.data.awsinterface.api.response;

public class RegisterResult extends RequestResult {

    private boolean inserted;
    private boolean duplicate;

    public RegisterResult(byte[] rawResponse) {
        super(rawResponse);
    }

    public boolean isDuplicate(){
        return duplicate;
    }

    public boolean isInserted(){
        return inserted;
    }

    public boolean isErrorUnknown(){
        return !(inserted && duplicate);
    }
}
