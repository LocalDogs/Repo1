package com.example.localdogs.data.awsinterface.api.response;

import androidx.annotation.NonNull;

import com.amplifyframework.auth.AuthException;

public class RegisterException extends AuthException{
    private boolean isInserted;
    private boolean isLoggedIn;
    public RegisterException(@NonNull String message, @NonNull Throwable cause, @NonNull String recoverySuggestion, boolean isInserted, boolean isLoggedIn) {
        super(message, cause, recoverySuggestion);
        this.isInserted = isInserted;
        this.isLoggedIn = isLoggedIn;
    }
}
