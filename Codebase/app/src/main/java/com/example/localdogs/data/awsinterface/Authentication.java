package com.example.localdogs.data.awsinterface;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.auth.result.AuthSignUpResult;
import com.amplifyframework.core.Action;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.rx.RxAmplify;
import com.google.android.play.core.assetpacks.AssetPackException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class Authentication {
    private static Authentication instance;
    private static Context context;
    private static boolean isAuthenticated;
    private static String activeUserEmail;

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

    protected synchronized void updateActiveUserEmail(String email){
        activeUserEmail = email;
    }

    public void signInUser(String email, String password, Consumer<AuthSignInResult> success, Consumer<AuthException> failure){
        Amplify.Auth.signIn(email, password, success, failure);
    }

    public void signOutUser(Action success, Consumer<AuthException> failure){
        Amplify.Auth.signOut(success, failure);
    }

    public void registerUser(String email, String password, JSONObject userData, Consumer<JSONObject> onSuccess, Consumer<AuthException> failure){
        Amplify.Auth.signUp(
                email,
                password,
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email).build(),
                (response) -> {

                    /** upload user to our database
                     * then call provided callback
                     * updateAuthenticatedStatus(true);
                     **/

                    Log.i("Auth", response.toString());
                    UserRequests ur = new UserRequests();
                    ur.uploadUserInfo(userData, (success) -> {
                        Log.i("RegisterUser", "uploading new user to db");
                        try {

                            JSONObject responseToCaller = success.getData().asJSONObject();
                            responseToCaller.put("NextStep", response.getNextStep());
                            updateActiveUserEmail(userData.getString("email"));
                            onSuccess.accept(responseToCaller);

                        } catch (JSONException e) {

                            throw new AssertionError("This literrally should never be an issue, but here we are");

                        }
                    }, (error) -> {

                        Log.e("RegisterUser", error.getMessage(), error.getCause());

                    });

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
                List<AuthUserAttribute> attrUser = RxAmplify.Auth.fetchUserAttributes().blockingGet();
                // if the session is good, then grab the active users email for future api calls
                AuthUserAttributeKey email = AuthUserAttributeKey.email();
                for(int i = 0; i < attrUser.size(); i++){
                    if(attrUser.get(i).getKey().equals(email)){

                        Authentication.getInstance(context).updateActiveUserEmail(attrUser.get(i).getValue());
                        Log.i("Auth", "ActiveUser=" + attrUser.get(i).getValue());
                        break;

                    }
                }

                break;
            case FAILURE:

                Log.e("Auth", "Session has expired", currentSession.getAWSCredentials().getError());
                Authentication.getInstance(context).updateAuthenticatedStatus(false);

            default:
                Log.e("Auth", "SHRUG");
                break;
        }
    }
}
