package com.example.localdogs;

import android.Manifest;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.internal.platform.content.PermissionGranter;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.example.localdogs.data.RequestQueueSingleton;
import com.example.localdogs.data.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import androidx.test.rule.GrantPermissionRule;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RequestQueueTester {
    @Rule
    public GrantPermissionRule mRunTimePermissionRule = GrantPermissionRule.grant(Manifest.permission.INTERNET);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        RequestQueue rq = RequestQueueSingleton.getInstance(appContext).getRequestQueue();
        //User user = new User("Randy", "Savage", "heehaw@aol.com", "05/02/1969");
        //JSONObject jsonobj = new Gson().fromJson(user.serializeToJson(), JSONObject.class);
        String url = "https://njxnl2knh4.execute-api.us-east-2.amazonaws.com/beeta/user/retrieve?email=heehaw@aol.com";
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET, url, null, future, future);
        RequestQueueSingleton.getInstance(appContext).addToRequestQueue(jsonReq);
        try{
            JSONArray response = future.get();
            System.out.println(response);
        }catch(InterruptedException e) {
            e.printStackTrace();
            System.out.println("fail1");
        }catch(ExecutionException e){
            e.printStackTrace();
            System.out.println("fail2");
        }
        assertEquals("com.example.localdogs", appContext.getPackageName());

    }
}