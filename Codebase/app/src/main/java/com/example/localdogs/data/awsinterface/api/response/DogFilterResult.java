package com.example.localdogs.data.awsinterface.api.response;

import com.example.localdogs.data.Dog;

import java.util.ArrayList;

public class DogFilterResult extends RequestResult{

    private ArrayList<Dog> dogs;
    private String query;

    public DogFilterResult(byte[] rawResponse) {
        super(rawResponse);
        // parse dogs
        // parse query
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    public ArrayList<Dog> getDogs(){
        return dogs;
    }

    public String getQuery(){
        return query;
    }
}
