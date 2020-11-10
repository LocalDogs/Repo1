package com.example.localdogs.data.awsinterface.api;

import android.util.Log;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.api.rest.RestResponse;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.example.localdogs.data.awsinterface.api.Requests;
import com.example.localdogs.data.awsinterface.api.response.DogFilterResult;
import com.example.localdogs.data.awsinterface.api.response.RetrieveUserResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DogRequests extends Requests {
    private String rsUploadDog;
    private String rsRetrieveDogs;
    private String rsUpdateDog;

    public DogRequests(){
        super();
        rsUploadDog = ApiResources.uploadDog();
        rsRetrieveDogs = ApiResources.retrieveDogs();
        rsUpdateDog = ApiResources.updateDog();
    }

    @Override
    public void getData(Map<String, String> query, String resource, Consumer onSuccess, Consumer<ApiException> onFailure){
        RestOptions options = RestOptions.builder()
                .addPath(resource)
                //.addQueryParameters(query)
                .build();
        Amplify.API.get(options, (success) -> {
            // do some stuff?
            Log.i("getData", success.getData().asString());
            onSuccess.accept(new DogFilterResult(success.getData().getRawBytes()));
        }, (error) -> {
            // do some stuff?
            onFailure.accept(error);
        });
    }

    /**
     * Just calling an updateUser method on the backend with the user's updated dog list
     *
     * Before calling, update a the active User's object with the dog list by appending or whatever
     */
    /*public void uploadDogInfo(JSONObject user, Consumer<RestResponse>onSuccess, Consumer<ApiException> onFailure){
        super.postData(user, rsUploadDog, onSuccess, onFailure);
    }*/

    /*public void updateDogInfo(JSONObject user, Consumer<RestResponse> onSuccess, Consumer<ApiException> onFailure){
        super.postData(user, rsUpdateDog, onSuccess, onFailure);
    }*/
}
