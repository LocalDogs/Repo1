package com.example.localdogs.data.awsinterface.api;

import android.util.Log;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.example.localdogs.data.awsinterface.api.response.DogFilterResult;
import com.example.localdogs.data.awsinterface.api.response.RetrieveUserResult;
import com.example.localdogs.data.awsinterface.api.response.UpdateResult;
import com.example.localdogs.data.awsinterface.api.response.UploadResult;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class Requests {

    public void getData(Map<String, String> query, String resource, Consumer onSuccess, Consumer<ApiException> onFailure){
        RestOptions options = RestOptions.builder()
                .addPath(resource)
                .addQueryParameters(query)
                .build();
        Amplify.API.get(options, (success) -> {
            // do some stuff?
            Log.i("getData", success.getData().asString());
            onSuccess.accept(new RetrieveUserResult(success.getData().getRawBytes()));
        }, (error) -> {
            // do some stuff?
            onFailure.accept(error);
        });
    }

    public void updateData(JSONObject data, String resource, Consumer onSuccess, Consumer<ApiException> onFailure){
        RestOptions options = generatePostOptions(data, resource);
        Amplify.API.post(options, (success) -> {
            // do some stuff?
            Log.i("updateData", "calling onSuccess callback");
            Log.i("updateData", success.getData().asString());
            UpdateResult uploadResult = new UpdateResult(success.getData().getRawBytes());
            onSuccess.accept(uploadResult);
        }, (error) -> {
            // do some stuff?
            Log.e("updateData", "calling onFailure callback");
            onFailure.accept(error);
        });
    }

    public void postData(JSONObject data, String resource, Consumer onSuccess, Consumer<ApiException> onFailure){
        RestOptions options = generatePostOptions(data, resource);
        Amplify.API.post(options, (success) -> {
            // do some stuff?
            Log.i("postData", "calling onSuccess callback");
            Log.i("postData", success.getData().asString());
            UploadResult uploadResult = new UploadResult(success.getData().getRawBytes());
            onSuccess.accept(uploadResult);
            }, (error) -> {
                // do some stuff?
                Log.e("postData", "calling onFailure callback");
                onFailure.accept(error);
            });
    }

    private RestOptions generatePostOptions(JSONObject data, String resource){
        Log.i("generatePostOptions", "packaging data");
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
        return options;
    }
}
