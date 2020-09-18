package com.example.localdogs;

import android.Manifest;
import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.localdogs.data.User;
import com.example.localdogs.data.UserRequests;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.rule.GrantPermissionRule;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RequestsTester {
    @Rule
    public GrantPermissionRule mRunTimePermissionRule = GrantPermissionRule.grant(Manifest.permission.INTERNET);
    @Test
    public void uploadUserTest() throws InterruptedException {
        // Context of the app under test.
        final Object syncObject = new Object();
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserRequests userRequests = new UserRequests(appContext);
        User user = new User("Randy", "Savage", "heehaw@aol.com", "05/02/1969");

        /*
        takes a User object as a parameter, Response.Listener for success handling,
        and a Response.ErrorListener for failure handling
        response is a json object with the following fields:
        "modifiedCount", "upsertId", "upsertedCount", "matchedCount"
            if an email already exists, this can be checked by looking at either
            upsertedCount, or matchedCount
                upsertedCount == 1 if the email was unique, 0 if not
                matchedCount == 1 if the email was no unique, 0 if it is
                if either of these values are > 1, would be an exceptional case,
                and should probably be handled -- it shouldn't happen
        */
        userRequests.uploadNewUser(user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                /*
                The server will respond with whether or not the request succeeded in
                adding the user to the database.
                 */
                try {
                    System.out.println(response.getInt("upsertedCount"));
                    System.out.println(response.getInt("matchedCount"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(response.toString());
                synchronized (syncObject) {
                    syncObject.notify();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        // need to test UserRequests.retrieveUser later

        synchronized (syncObject) {
            syncObject.wait();
        }
        assertEquals("com.example.localdogs", appContext.getPackageName());
    }
    @Test
    public void retrieveUserTest() throws InterruptedException {
        // Context of the app under test.
        final Object syncObject = new Object();
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserRequests userRequests = new UserRequests(appContext);

        /*
        asynchronous request for a user profile specified
        by their email (which should be unique)
         */
        userRequests.retrieveUserProfile("heehaw@aol.com", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());

                // create a user object from database data
                User user = User.toUser(response);

                System.out.println(user.toString());

                // synchronized block only necessary for testing
                // it allows the request thread to finish before exiting test
                synchronized (syncObject) {
                    syncObject.notify();
                }
                /*
                Add whatever code you want to execute here AFTER data has been
                returned from the server, populate fields in view, whatever.
                */

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                /*
                Some error in getting the request occurred, whether no user existed, or
                the request was rejected
                */
            }
        });
        // need to test UserRequests.retrieveUser later
        assertEquals("com.example.localdogs", appContext.getPackageName());

        // force main thread to wait for other request thread
        synchronized (syncObject) {
            syncObject.wait();
        }

    }

}