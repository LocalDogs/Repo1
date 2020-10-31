package com.example.localdogs.data.awsinterface.api;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.api.rest.RestResponse;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class Requests {

    public void getData(Map<String, String> query, String resource, Consumer<ProfileResult> onSuccess, Consumer<ApiException> onFailure){
        RestOptions options = RestOptions.builder()
                .addPath(resource)
                .addQueryParameters(query)
                .build();
        Amplify.API.get(options, (success) -> {
            // do some stuff?
            onSuccess.accept(new ProfileResult(success.getData().getRawBytes(), "Retrieved Data Successfully"));
        }, (error) -> {
            // do some stuff?
            onFailure.accept(error);
        });
    }

    public void postData(JSONObject data, String resource, Consumer<RestResponse> onSuccess, Consumer<ApiException> onFailure){
        byte[] bdata = new byte[0];
        try {
            bdata = data.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is not supported -- if this throws, pull your money out of the stock market");
        }
        RestOptions options = RestOptions.builder()
                .addPath(resource)
                .addHeader("Content-Type", "application/json")
                .addBody(bdata)
                .build();
        Amplify.API.post(options, (success) -> {
            // do some stuff?
            onSuccess.accept(success);
            }, (error) -> {
                // do some stuff?
                onFailure.accept(error);
            });
    }
}
