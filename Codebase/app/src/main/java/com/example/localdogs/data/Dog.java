package com.example.localdogs.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.localdogs.dob;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dog {

    private String name;
    private ArrayList<String> breeds;
    private dob age;
    private int weight;
    private String owner;
    private int activityLevel;
    private String imgurl;

    public Dog (String owner, String name, ArrayList<String> breeds, dob age, int weight, int activityLevel, String imgurl){
        this(owner, name, breeds, age, weight, activityLevel);
        this.imgurl = imgurl;
    }
    public Dog (String owner, String name, ArrayList<String> breeds, dob age, int weight, int activityLevel){
        this.name = name;
        this.owner = owner; //email of user
        this.breeds = breeds;
        this.age = age;
        this.weight = weight;
        this.activityLevel = activityLevel;
        int index = owner.indexOf("@");
        this.imgurl = owner.substring(0, index) + "/" + name + ".png";
    }

    public void setImgUrl(String imgurl){
        this.imgurl = imgurl;
    }

    public String getImgUrl(){
        return this.imgurl;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return this.name; }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getActivityLevel() {
        return activityLevel;
    }

    public String getActivityLevelAsString() {
        String s = "";
        for(int i=0; i<getActivityLevel(); i++)
            s += "★";
        for(int i=0; i<5-getActivityLevel(); i++)
            s += "☆";
        return s;
    }

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setWeight(int weight) { this.weight = weight; }

    public int getWeight() { return this.weight; }

    public void setBreeds(ArrayList<String> breeds) {
        this.breeds = breeds;
    }

    public ArrayList<String> getBreeds() { return this.breeds; }

    public dob getDOB(){
        return age;
    }

    public String getBreedsAsString() {
        String b = "";
        if(this.isMixed()) {
            for (int i = 0; i < getBreeds().size()-1; i++)
                b += getBreeds().get(i) + " / ";
            b += getBreeds().get(getBreeds().size()-1)+ " [Mixed]";
        }
        else b = this.getBreeds().get(0);
        return b;
    }

    public int getAge() {
        return age.getAgeYears();
    }

    public void setAge(dob age) {
        this.age = age;
    }

    public Boolean isMixed(){ return (breeds.size() > 1); }

    /**
     * This method is to turn a Dog class object into a json object.
     * @param
     * @return A JSONObject object representation of a Dog class object.
     */
    public JSONObject toJSONObject(){

        JSONObject jsonUser = new JSONObject();
        JSONArray dogArray = new JSONArray();
        try {

            jsonUser.put("owner", getOwner());
            jsonUser.put("name", getName());
            jsonUser.put("age", getDOB().toString());
            jsonUser.put("activitylevel",getActivityLevel()); // temporary
            for(String breed : breeds){

                dogArray.put(breed);

            }
            jsonUser.put("breeds", dogArray);
            jsonUser.put("mixed", isMixed());
            jsonUser.put("weight", getWeight());
            jsonUser.put("imgurl", getImgUrl());

        } catch (JSONException e) {

            e.printStackTrace();

        }
        return jsonUser;
    }

    public static Dog toDog(JSONObject jsonDog){

        Dog dog = null;
        try {
            ArrayList<String> breedsList = new ArrayList<String>();
            for(int i = 0; i < jsonDog.getJSONArray("breeds").length(); i++){

                breedsList.add(jsonDog.getJSONArray("breeds").getString(i));

            }
            dog = new Dog
                    (
                            jsonDog.getString("owner"),
                            jsonDog.getString("name"),
                            breedsList,
                            //we're storing age as a DOB object, not an int
                            new dob(jsonDog.getString("age")),
                            jsonDog.getInt("weight"),
                            jsonDog.getInt("activitylevel"),
                            jsonDog.getString("imgurl")
                    );
        } catch (JSONException e) {

            e.printStackTrace();
            Log.d("Dog Class", "Dog.toDog conversion error");

        }
        return dog;
    }

    @NonNull
    @Override
    public String toString(){
        return this.toJSONObject().toString();
    }

}
