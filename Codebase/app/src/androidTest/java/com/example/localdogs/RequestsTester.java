package com.example.localdogs;

import android.Manifest;
import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.localdogs.data.User;
import com.example.localdogs.data.UserRequests;

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
        userRequests.uploadNewUser(user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
        assertEquals("com.example.localdogs", appContext.getPackageName());
        synchronized (syncObject) {
            syncObject.wait();
        }
    }
    @Test
    public void retrieveUserTest() throws InterruptedException {
        // Context of the app under test.
        final Object syncObject = new Object();
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserRequests userRequests = new UserRequests(appContext);
        userRequests.retrieveUserProfile("heehaw@aol.com", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
        assertEquals("com.example.localdogs", appContext.getPackageName());
        synchronized (syncObject) {
            syncObject.wait();
        }

    }

}