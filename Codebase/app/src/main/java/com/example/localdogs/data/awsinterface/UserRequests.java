package com.example.localdogs.data.awsinterface;

import com.amplifyframework.api.ApiException;
import com.amplifyframework.api.rest.RestResponse;
import com.amplifyframework.core.Consumer;
import com.example.localdogs.data.awsinterface.Requests;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserRequests extends Requests {
    private String rsUploadUser;
    private String rsRetrieveUser;

    public UserRequests(){
        super();
        rsUploadUser = "/uploadUser";
        rsRetrieveUser = "/retrieveUser";
    }

    public void retrieveUserInfo(String email, Consumer<RestResponse>onSuccess, Consumer<ApiException> onFailure){
        Map<String, String> query = new HashMap<String, String>();
        query.put("email", email);
        super.getData(query, rsRetrieveUser, onSuccess, onFailure);
    }

    public void uploadUserInfo(JSONObject newUser, Consumer<RestResponse>onSuccess, Consumer<ApiException> onFailure){
        super.postData(newUser, rsUploadUser, onSuccess, onFailure);
    }
}
