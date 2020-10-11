package com.example.localdogs.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dog {

    private String name;
    private ArrayList<String> breeds;
    private int age;
    private int weight;
    private String owner;
    private int activityLevel;

    public Dog (String owner, String name, ArrayList<String> breeds, int age, int weight, int activityLevel){
        this.name = name;
        this.owner = owner; //email of user
        this.breeds = breeds;
        this.age = age;
        this.weight = weight;
        this.activityLevel = activityLevel;
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

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setWeight(int weight) { this.weight = weight; }

    public int getWeight() { return this.weight; }

    public void setBreeds(ArrayList<String> breeds) {
        this.breeds = breeds;
    }

    public ArrayList<String> getBreeds() { return this.breeds; }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean isMixed(){ return (breeds.size() > 2); }

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
            jsonUser.put("age", getAge());
            jsonUser.put("activityLevel",getActivityLevel()); // temporary
            for(String breed : breeds){

                dogArray.put(breed);

            }
            jsonUser.put("breeds", dogArray);
            jsonUser.put("mixed", isMixed());

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
                            jsonDog.getInt("age"),
                            jsonDog.getInt("weight"),
                            jsonDog.getInt("activityLevel")
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
