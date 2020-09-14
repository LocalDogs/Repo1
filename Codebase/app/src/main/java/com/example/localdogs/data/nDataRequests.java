package com.example.localdogs.data;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class nDataRequests {
    String url = "http://my-json-feed";

    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("Response: " + response.toString());
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    System.err.println("error occurrrrred:)");
                }
            });
}
