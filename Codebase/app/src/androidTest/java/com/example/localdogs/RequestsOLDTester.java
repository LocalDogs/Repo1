package com.example.localdogs;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.localdogs.data.User;
import com.example.localdogs.data.awsinterface.UserRequestsOLD;

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
public class RequestsOLDTester {
    @Rule
    public GrantPermissionRule mRunTimePermissionRule = GrantPermissionRule.grant(Manifest.permission.INTERNET);
    @Test
    public void uploadUserTest() throws InterruptedException {
        // Context of the app under test.
        final Object syncObject = new Object();
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserRequestsOLD userRequests = new UserRequestsOLD(appContext);
//        User user = new User("Josephine", "Grady", "yabbadoo@aol.com", "05/10/1980", "tacosraight");

        /*
        takes a User object as a parameter, Response.Listener for success handling,
        and a Response.ErrorListener for failure handling

        response is a json object with the following fields:
        "statusCode" and "body", which depending on the value of "statusCode" may contain
        either "upsertedId" or "error"

        if statusCode == 200 then a successful connection was made with the database and the
        following checks can be made:
            if an email already exists, this can be checked by checking if
            upsertedID is null or not, usertedId will NOT be null if the new user was
            inserted into the database, it WILL be null if the email already exists
        if statusCode == 500, then the connection with the database failed to be made:
            for the most part, this should probably never happen unless the app does not have access
            to the internet -- I suspect AWS and MongoDB Atlas rarely has downtime.
            probably should just notify the user to make sure they are connected to the internet
            or something in this case
            might could right another class to handle this stuff dunno
        */
        /*userRequests.uploadNewUser(user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                *//*
                The server will respond with whether or not the request succeeded in
                adding the user to the database.

                if response is null for some reason, onError listener will be evoked
                    instead
                 *//*
                try {
                    if(response.getInt("statusCode") == 200 && response.getBoolean("inserted")){

                        System.out.println(response.getString("message"));

                    }
                    else if(response.getInt("statusCode") == 500){

                        System.out.println("Connection failed to be made with database");

                    }
                    else{

                        System.out.println("Email already exists");

                    }
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
                Log.d("uploadNewUser.onErrorResponse", "yikes");

                synchronized (syncObject) {

                    syncObject.notify();

                }
            }
        });
        // need to test UserRequests.retrieveUser later

        synchronized (syncObject) {

            syncObject.wait();

        }*/
        assertEquals("com.example.localdogs", appContext.getPackageName());
    }
    @Test
    public void retrieveUserTest() throws InterruptedException {

        // Context of the app under test.
        final Object syncObject = new Object();
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserRequestsOLD userRequests = new UserRequestsOLD(appContext);

        /*
        asynchronous request for a user profile specified
        by their email (which should be unique)
        response will be null if the user does not exist
         */
        userRequests.retrieveUserProfile("yabbadoo@aol.com", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getInt("statusCode") == 200 && response.getBoolean("found")) {

                        System.out.println(response.toString());

                        // create a user object from database data
                        User user = User.toUser(response);

                        System.out.println(user.toString());

                        // synchronized block only necessary for testing
                        // it allows the request thread to finish before exiting test

                    }
                    else if(response.getInt("statusCode") == 500){

                        System.out.println("Connection failed to be made with database");

                    }
                    else{

                        System.out.println("Email was not found");

                    }
                } catch (JSONException e) {

                    e.printStackTrace();

                }

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
                    This executes mostly if the response is null for whatever reason.
                */
                Log.d("retrieveUserTest.onErrorResponse", "yikes");

                synchronized (syncObject) {
                    syncObject.notify();
                }
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