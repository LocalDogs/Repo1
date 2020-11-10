package com.example.localdogs.data.awsinterface.api;

import android.util.Log;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.rest.RestResponse;
import com.amplifyframework.core.Consumer;
import com.example.localdogs.data.awsinterface.api.response.RetrieveUserResult;
import com.example.localdogs.data.awsinterface.api.response.UploadResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserRequests extends Requests {
    private String rsUploadUser;
    private String rsRetrieveUser;
    private String rsUpdateUser;

    public UserRequests(){
        super();
        rsUploadUser = ApiResources.uploadUser();
        rsRetrieveUser = ApiResources.retrieveUser();
        rsUpdateUser = ApiResources.updateUser();
    }

    public void retrieveUserInfo(String email, Consumer<RetrieveUserResult>onSuccess, Consumer<ApiException> onFailure){
        Map<String, String> query = new HashMap<String, String>();
        query.put("email", email);
        super.getData(query, rsRetrieveUser, onSuccess, onFailure);
    }

    public void uploadUserInfo(JSONObject newUser, Consumer<UploadResult> onSuccess, Consumer<ApiException> onFailure){
        Log.i("uloadUserInfo", "sending data");
        super.postData(newUser, rsUploadUser, onSuccess, onFailure);
    }

    /*public void updateUserInfo(JSONObject user, Consumer<RestResponse>onSuccess, Consumer<ApiException> onFailure){
        super.postData(user, rsUpdateUser, onSuccess, onFailure);
    }*/
}
