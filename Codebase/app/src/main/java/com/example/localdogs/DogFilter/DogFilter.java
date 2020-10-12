package com.example.localdogs.DogFilter;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class DogFilter {

    private static DogFilter instance;
    private static Context ctx;
    private int activityLevel;
    private int minWeight;
    private int maxWeight;
    private int minAge;
    private int maxAge;
    private String breed1;
    private String breed2;

    private DogFilter(Context ctx){
        this.activityLevel = -1;
        this.maxWeight = -1;
        this.minAge = -1;
        this.maxAge = -1;
        this.breed1 = "";
        this.breed2 = "";
    }

    public static DogFilter getInstance(Context ctx){
        if(instance == null) instance = new DogFilter(ctx);
        return instance;
    }

    public void setActivityLevel(int activityLevel){
        this.activityLevel = activityLevel;
    }

    public void setMinAge(int minAge){
        this.minAge = minAge;
    }

    public void setMaxAge(int maxAge){
        this.maxAge = maxAge;
    }

    public void setMinWeight(int minWeight){
        this.minWeight = minWeight;
    }

    public void setMaxWeight(int maxWeight) { this.maxWeight = maxWeight; }

    public void setBreed1(String breed1){
        this.breed1 = breed1;
    }

    public void setBreed2(String breed2){
        this.breed2 = breed2;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public int getMinWeight() {
        return minWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public String getBreed1() {
        return breed1;
    }

    public String getBreed2() {
        return breed2;
    }

    @NonNull
    @Override
    public String toString(){

        return this.toJSONObject().toString();

    }
    public JSONObject toJSONObject(){

        JSONObject jsonDogFilter = new JSONObject();
        try {

            jsonDogFilter.put("activityLevel", activityLevel);
            jsonDogFilter.put("minWeight", minWeight);
            jsonDogFilter.put("maxWeight", maxWeight);
            jsonDogFilter.put("minAge", minAge); // temporary
            jsonDogFilter.put("MaxAge", maxAge);
            jsonDogFilter.put("breed1", breed1);
            jsonDogFilter.put("breed2", breed2);

        } catch (JSONException e) {

            e.printStackTrace();

        }
        return jsonDogFilter;
    }
}