package com.example.localdogs;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.rx.RxAmplify;
import com.example.localdogs.data.User;
import com.example.localdogs.data.awsinterface.AmplifyHub;
import com.example.localdogs.data.awsinterface.Authentication;
import com.example.localdogs.data.awsinterface.Image;

import org.junit.Test;

import static android.content.ContentValues.TAG;

public class AuthTest {

    @Test
    public void testAuth() throws InterruptedException {
        final Object object = new Object();
        User newUser = new User("Trevor", "Lockhart", "anotherone@anotherone.com", "01/01/2001");
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AmplifyHub.launchAmplify(appContext);
        Amplify.Auth.fetchAuthSession(
                result -> {
                    AWSCognitoAuthSession cognitoAuthSession = (AWSCognitoAuthSession) result;
                    switch(cognitoAuthSession.getIdentityId().getType()) {
                        case SUCCESS:
                            Log.i("AuthQuickStart", "IdentityId: " + cognitoAuthSession.getIdentityId().getValue());
                            synchronized (object){
                                object.notify();
                            }
                            break;
                        case FAILURE:
                            Log.i("AuthQuickStart", "IdentityId not present because: " + cognitoAuthSession.getIdentityId().getError().toString());
                            synchronized (object){
                                object.notify();
                            }
                    }
                },
                error -> Log.e("AuthQuickStart", error.toString())
        );
        /*AWSMobileClient mobileClient = (AWSMobileClient) Amplify.Auth.getPlugin("awsCognitoAuthPlugin").getEscapeHatch();
        mobileClient.initialize(appContext, new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails result) {
                UserState currentUserState = AWSMobileClient.getInstance().currentUserState().getUserState();
                Log.i("Testing", result.getUserState().toString());
                Log.i("Testing", currentUserState.toString());
                Authentication.getInstance(appContext).registerUser(newUser.getEmail(), "remember!@#$1234", newUser, success -> {
                    Log.i("Testing", "success?");
                    synchronized (object){
                        object.notify();
                    }
                }, error -> {
                    Log.e("Testing", error.getMessage());
                    synchronized (object){
                        object.notify();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                synchronized (object){
                    object.notify();
                }
            }
        });*/
        /*
        Authentication.getInstance(appContext).registerUser("omg@omg.com", "remember!@#$1234", newUser, success -> {
            Log.i("Registration", success.toString());
            synchronized (object){
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.e("Registration", error.getMessage());
            synchronized (object){
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*/
        //UserStateDetails usd = AWSMobileClient.getInstance().currentUserState();

        synchronized (object){
            object.wait();
        }
    }

}
