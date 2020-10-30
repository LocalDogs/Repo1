package com.example.localdogs.data.awsinterface.api;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.rest.RestResponse;
import com.amplifyframework.core.Consumer;
import com.example.localdogs.data.awsinterface.api.Requests;

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

    public void retrieveDogInfo(String email, Consumer<RestResponse>onSuccess, Consumer<ApiException> onFailure){
        Map<String, String> query = new HashMap<String, String>();
        query.put("email", email);
        super.getData(query, rsRetrieveDogs, onSuccess, onFailure);
    }

    /**
     * Just calling an updateUser method on the backend with the user's updated dog list
     * Before calling, update a User object with the dog list by appending or whatever
     */
    public void uploadDogInfo(JSONObject user, Consumer<RestResponse>onSuccess, Consumer<ApiException> onFailure){
        super.postData(user, rsUploadDog, onSuccess, onFailure);
    }

    public void updateDogInfo(JSONObject user, Consumer<RestResponse> onSuccess, Consumer<ApiException> onFailure){
        super.postData(user, rsUpdateDog, onSuccess, onFailure);
    }
}
