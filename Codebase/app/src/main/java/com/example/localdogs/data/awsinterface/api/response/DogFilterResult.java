package com.example.localdogs.data.awsinterface.api.response;

import android.util.Log;

import com.example.localdogs.DogFilter.DogFilter;
import com.example.localdogs.data.Dog;
import com.example.localdogs.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DogFilterResult extends RequestResult{

    private ArrayList<User> users;
    private String query;

    public DogFilterResult(byte[] rawResponse) {
        super(rawResponse);
        users = new ArrayList<User>();
        query = "";
        try {
            JSONArray dogs = getResponseAsJSONObject().getJSONArray("dogs");
            query = getResponseAsJSONObject().getString("query");
            if (query == null) query = "";
            Log.i("DogFilterResult", dogs.toString());
            for(int i = 0; i < dogs.length(); i++){
                users.add(User.toUser(dogs.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // parse query
    }

    @Override
    public boolean isSuccess() {
        if(users != null) return true;
        return false;
    }

    public ArrayList<User> getDogs(){
        return users;
    }

    public String getQuery(){
        return query;
    }
}
