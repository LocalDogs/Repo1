package com.example.localdogs.data.awsinterface;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthChannelEventName;
import com.amplifyframework.auth.AuthSession;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.InitializationStatus;
import com.amplifyframework.hub.HubChannel;
import com.amplifyframework.rx.RxAmplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.example.localdogs.data.User;

public class AmplifyHub {

    private static Context context;
    private static AmplifyHub instance;

    private AmplifyHub(Context context){
        this.context = context;
        try {
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(context);
            Amplify.Auth.initialize(context);
            Authentication.getInstance(context.getApplicationContext());
            Log.i("AmplifyHub", "Initialized Amplify");
        } catch (AmplifyException error) {
            // this shouldn't happen
            Log.e("AmplifyHub", "Could not initialize Amplify", error);
        }
    }

    /**
     * synchronized keyword makes this method call thread safe
     * initializes Amplify services app-wide, this should only
     * be called in the login screen (starting activity)
     * multiple calls will have no effect
     * might should actually just start the app with a lightweight
     * "Loading Your Data" screen, then either launch the cardstack or
     * the LogIn screen based on the results of Authorization.isSignedIn(),
     * which is set in this constructor
     */
    public static synchronized void launchAmplify(Context context){
        if(instance == null) instance = new AmplifyHub(context.getApplicationContext());
        // i may need to return the instance here at some point
        // but doubtful
        // just wanna guarantee when going back to the login screen, this isn't run again
    }

    private void subscribeToHub(){
        Amplify.Hub.subscribe(HubChannel.AUTH, hubEvent -> {
            if (hubEvent.getName().equals(InitializationStatus.SUCCEEDED.toString())) {
                Log.i("AuthQuickstart", "Auth successfully initialized");
            } else if (hubEvent.getName().equals(InitializationStatus.FAILED.toString())){
                Log.i("AuthQuickstart", "Auth failed to succeed");
            } else {
                switch (AuthChannelEventName.valueOf(hubEvent.getName())) {
                    case SIGNED_IN:
                        /**
                         * handling this case in Authentication.registerUser() method
                         * so we know exactly when we set the isSignedIn flag to true
                         */
                        Log.i("Auth", "User just became signed in.");
                        break;
                        case SIGNED_OUT:
                            Log.i("Auth", "User just became signed out.");
                            /**
                             * Should notify user here that they logged out
                             * this should be something that only happens if they choose it,
                             * by clicking on a logout button or something and can be handled
                             * wherever that happens, not here
                             */
                            break;
                        case SESSION_EXPIRED:
                            /**
                             * Should notify user here, launch login screen or whatever
                             * this will happen when their token expires, 7 days after every
                             * login (currently 1 hour for testing)
                             * ** (10/28/2020, 11:21pm) NOTE: this isn't getting emitted, don't know why
                             * ** ok, this case will have to be handled in onError callbacks during
                             * ** API calls
                             * ** This event not firing is apparently common, there an issue in the
                             * ** Amplify github
                             * ** this worked randomly?
                             */
                            Log.i("Auth", "Auth session just expired.");
                            break;
                        default:
                            /**
                             * This is some yikes shit, shouldn't happen
                             */
                            Log.w("Auth", "Unhandled Auth Event: " + AuthChannelEventName.valueOf(hubEvent.getName()));
                            break;
                }
            }
        });
    }
}
