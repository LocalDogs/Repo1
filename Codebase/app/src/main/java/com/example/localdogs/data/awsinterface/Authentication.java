package com.example.localdogs.data.awsinterface;

import android.content.Context;
import android.util.Log;

import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Action;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.rx.RxAmplify;

import org.json.JSONObject;

public class Authentication {
    private static Authentication instance;
    private static Context context;
    private boolean isAuthenticated;

    private Authentication(Context context){
        this.context = context;
        updateAuthenticatedStatus(false);
    }

    public static synchronized Authentication getInstance(Context context){
        if(instance == null) instance = new Authentication(context.getApplicationContext());
        return instance;
    }
    public Boolean isSessionGood(){
        return isAuthenticated;
    }

    protected synchronized void updateAuthenticatedStatus(Boolean bool){
        isAuthenticated = bool;
    }

    public void signInUser(String email, String password, Consumer<AuthSignInResult> success, Consumer<AuthException> failure){
        Amplify.Auth.signIn(email, password, success, failure);
    }
    public void signOutUser(Action success, Consumer<AuthException> failure){
        Amplify.Auth.signOut(success, failure);
    }
    public void registerUser(String email, String password, JSONObject userData, Consumer<AuthSignUpResult> onSuccess, Consumer<AuthException> failure){
        Amplify.Auth.signUp(
                email,
                password,
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email).build(),
                (response) -> {
                    // upload user to our database
                    // then call provided callback
                    //updateAuthenticatedStatus(true);
                    onSuccess.accept(response);
                },
                (err) -> {
                    // can put stuff here if needed before passed in callback
                    failure.accept(err);
                });
    }
    public void checkInitAuth(){
        /**
         * if this is returns true, we can bypass the login screen
         * i think this should only be called once
         * THIS BLOCKS TILL RESULT IS RETURNED
         */
        AWSCognitoAuthSession currentSession = (AWSCognitoAuthSession) RxAmplify.Auth.fetchAuthSession().blockingGet();
        switch (currentSession.getAWSCredentials().getType()){
            case SUCCESS:
                Log.i("Auth", "Session still good");
                Authentication.getInstance(context).updateAuthenticatedStatus(true);
                break;
            case FAILURE:
                Log.e("Auth", "Session has expired", currentSession.getAWSCredentials().getError());
                Authentication.getInstance(context).updateAuthenticatedStatus(false);
        }
    }
}
