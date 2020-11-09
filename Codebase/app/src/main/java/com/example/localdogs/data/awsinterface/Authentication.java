package com.example.localdogs.data.awsinterface;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.core.Action;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.rx.RxAmplify;
import com.example.localdogs.data.User;
import com.example.localdogs.data.awsinterface.api.UserRequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Authentication {
    private static volatile Authentication instance;
    private static Context context;
    private static boolean isAuthenticated;
    private static CurrentSession currentSession;

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

    protected static synchronized Authentication getInstance() throws Exception {
        if (instance == null){
            throw new Exception("Authentication has not be initialized.");
        }
        return instance;
    }

    public void signInUser(String email, String password, Consumer<AuthSignInResult> onSuccess, Consumer<AuthException> onFailure){
        Amplify.Auth.signIn(email, password, (success) -> {

            UserRequests ur = new UserRequests();
            ur.retrieveUserInfo(email, (userProfile) -> {

                Log.i("signInUser", "Checking CurrentSession status");
                if(getCurrentSession() == null || !getCurrentSession().getCurrentSessionUserEmail().equals(email)) loadCurrentSession(userProfile.getUser());
                onSuccess.accept(success);

            }, (error) -> Log.e("signInUser", "Failed to get user profile from database"));

        }, onFailure);
    }

    public void signOutUser(Action onSuccess, Consumer<AuthException> onFailure){
        Amplify.Auth.signOut(() -> {
            Authentication.getInstance(context).updateAuthenticatedStatus(false);
            // if user signs out, i think we hold onto the CurrentSession instance
            // if a new user signs in, it will be replaced with the new user's session
            // if the same user logs in, then we're good
            onSuccess.call();

        }, onFailure);
    }

    public void registerUser(String email, String password, User userData, Consumer<JSONObject> onSuccess, Consumer<AuthException> failure){
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
                    updateCurrentSession(userData);
                    Amplify.Auth.signIn(email, password, success -> {

                        Log.i("registerUser", "Automatically logging user in after registration");
                        Log.i("UserRegistered", "Attempting to upload new user to database");
                        ur.uploadUserInfo(userData.toJSONObject(), (uploadUserSuccess) -> {

                            Log.i("RegisterUser", "uploading new user to db");
                            JSONObject responseToCaller = null;
                            try {

                                responseToCaller = uploadUserSuccess.getData().asJSONObject();
                                responseToCaller.put("NextStep", response.getNextStep());
                                userData.setId(responseToCaller.getString("userid"));
                                Authentication.getInstance(context).getCurrentSession().updateCurrentSessionUser(userData);

                            } catch (JSONException e) {

                                throw new AssertionError("This literally should never be an issue, but here we are");

                            }
                            onSuccess.accept(responseToCaller);

                    }, error -> {

                        Log.e("registerUser", "Automatically loggin in user failed", error.getCause());

                    });
                    }, (error) -> {
                        // bad query, email already exists
                        Log.e("RegisterUser", error.getMessage(), error.getCause());
                        failure.accept(new AuthException(error.getMessage(), error.getCause(), error.getRecoverySuggestion()));

                    });
                },
                (err) -> {
                    // registration error -- email already exists
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
        // pretty sure this call blocks
        UserStateDetails currentUserState = AWSMobileClient.getInstance().currentUserState();
        Log.i("checkInitAuth", currentUserState.getUserState().toString());
        // keep an eye on this, i think the 3rd and 4th cases will be true if the user is logged in
        // AND their credentials have expired, which is desired
        switch(currentUserState.getUserState()){

            case GUEST:
            case SIGNED_OUT:
            case SIGNED_OUT_USER_POOLS_TOKENS_INVALID:
            case SIGNED_OUT_FEDERATED_TOKENS_INVALID:
                updateAuthenticatedStatus(false);
                Log.i("AuthStatus", currentUserState.toString());
                break;
            case SIGNED_IN:
                updateAuthenticatedStatus(true);
                break;
            default:
                break;

        }
        /*AWSCognitoAuthSession currentSession = (AWSCognitoAuthSession) RxAmplify.Auth.fetchAuthSession().blockingGet();
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

                break;
            default:
                Log.e("Auth", "SHRUG: " + currentSession.getAWSCredentials().getType());
                break;
        }*/
    }

    private CurrentSession getCurrentSession(){
        // this COULD be null, hopefully we can prevent that
        return currentSession;
    }

    public User getCurrentSessionUser(){
        return currentSession.getCurrentSessionUser().getUserCopy();
    }

    private synchronized void loadCurrentSession(User user){
        currentSession = CurrentSession.getInstance(context);
        currentSession.updateCurrentSessionUser(user);
    }

    public synchronized void updateCurrentSession(User user){
        currentSession = CurrentSession.getInstance(context);
        currentSession.updateCurrentSessionUser(user);
    }
}
