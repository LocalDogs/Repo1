package com.example.localdogs.data.awsinterface;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.auth.AuthException;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.auth.result.AuthSignInResult;
import com.amplifyframework.core.Action;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.example.localdogs.data.User;
import com.example.localdogs.data.awsinterface.api.ApiResources;
import com.example.localdogs.data.awsinterface.api.DogRequests;
import com.example.localdogs.data.awsinterface.api.UserRequests;
import com.example.localdogs.data.awsinterface.api.response.CardStackDogList;
import com.example.localdogs.data.awsinterface.api.response.DogFilterResult;
import com.example.localdogs.data.awsinterface.api.response.RegisterException;
import com.example.localdogs.data.awsinterface.api.response.RegisterResult;

public class Authentication {
    private static volatile Authentication instance;
    private static Context context;
    private static boolean isAuthenticated;
    private static CurrentSession currentSession;

    private Authentication(Context context){
        this.context = context;
        updateAuthenticatedStatus(false);
    }
    private Context getContext(){
        return context;
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
                Authentication.getInstance(context).updateAuthenticatedStatus(true);
                Log.i("signInUser", "Checking CurrentSession status");
                /*if(getCurrentSession() == null || !getCurrentSession().getCurrentSessionUserEmail().equals(email)){
                    if(userProfile.getUser() == null){
                        onSuccess.accept(success);
                        Log.i("signInUser", "user was not found in database");

                    }
                    Authentication.getInstance(this.context).updateCurrentSession(userProfile.getUser());
                }*/
                if(userProfile.getUser() == null){
                    onSuccess.accept(success);
                    Log.i("signInUser", "user was not found in database");

                }
                Authentication.getInstance(this.context).updateCurrentSession(userProfile.getUser());
                DogRequests dogRequests = new DogRequests();
                dogRequests.getDogs
                        (
                                getCurrentSessionUser().getEmail(),
                                dogFilterSuccess -> {
                                    Log.i("DogFilter", "Retrieved Dogs");
                                    CardStackDogList.getInstance(getContext().getApplicationContext()).setDogs((DogFilterResult) dogFilterSuccess);
                                    onSuccess.accept(success);
                                    },
                                DogFilterError -> {
                                    Log.e("DogFilter", "Something went wrong :)");
                                }
                        );
                //onSuccess.accept(success);

            }, (error) -> {
                Log.e("signInUser", "Failed to get user profile from database");
                onFailure.accept(new AuthException("Couldn't Retrieve user", "Try retrieving again later"));
            });
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

    public void registerUser(String email, String password, User userData, Consumer<RegisterResult> onSuccess, Consumer<RegisterException> failure){
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
                        updateAuthenticatedStatus(true);

                        Log.i("registerUser", "Automatically logging user in after registration");
                        Log.i("registerUser", "Attempting to upload new user to database");
                        ur.uploadUserInfo(userData.toJSONObject(), (uploadUserSuccess) -> {
                            Log.i("registerUser", "uploading new user to db");
                            RegisterResult registerResult = new RegisterResult(uploadUserSuccess.getRawBytes(), true, true);
                            if(registerResult.isInserted()) updateCurrentSession(registerResult.getUser());
                            DogRequests dogRequests = new DogRequests();
                            /*
                            TODO:
                                  Need to figure out a cleaner way to populate the cardstack.
                            */
                            dogRequests.getDogs
                                    (
                                            Authentication.getInstance(context).getCurrentSessionUser().getEmail(),
                                            dogFilterSuccess -> {
                                Log.i("DogFilter", "Retrieved Dogs");
                                CardStackDogList.getInstance(getContext().getApplicationContext()).setDogs((DogFilterResult) dogFilterSuccess);
                                onSuccess.accept(registerResult);
                            }, DogFilterError -> {
                                Log.e("DogFiler", "Something went wrong :)");
                            });
                            // contains response from lambda function, whether success or failure
                            //onSuccess.accept(registerResult);

                        }, error -> {
                            RegisterResult registerException = new RegisterResult("Failed to upload new user", error.getMessage(), true, true);
                            onSuccess.accept(registerException);

                        });
                    }, (error) -> {

                        Log.e("registerUser", "Automatically logging in user failed", error.getCause());
                        RegisterResult registerResult = new RegisterResult("Failed to sign in and upload new user", error.getMessage(), false, true);
                        onSuccess.accept(registerResult);

                    });
                },
                (err) -> {
                    // registration error -- email already exists
                    // can put stuff here if needed before passed in callback
                    // this means the entire process failed
                    RegisterException registerException = new RegisterException(err.getMessage(), err.getCause(), err.getRecoverySuggestion(), false, false);
                    failure.accept(registerException);

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

    public CurrentSession getCurrentSession(){
        // this COULD be null, hopefully we can prevent that
        return currentSession;
    }

    public User getCurrentSessionUser(){
        return currentSession.getCurrentSessionUser().getUserCopy();
    }

    public synchronized void updateCurrentSession(User user){
        currentSession = CurrentSession.getInstance(context);
        currentSession.updateCurrentSessionUser(user.getUserCopy());
    }
}
