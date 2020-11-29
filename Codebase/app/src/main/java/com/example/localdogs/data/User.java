package com.example.localdogs.data;

import android.content.Context;
import android.service.autofill.FieldClassification;
import android.util.Log;

import androidx.annotation.NonNull;

import com.amplifyframework.core.Amplify;
import com.example.localdogs.dob;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class User implements Cloneable{
    private String firstname;
    private String lastname;
    private String email;
    private String dateofbirth;
    private String id;
    private HashMap<String, Dog> dogs;
    //--
    private String contactInfo;

    private ArrayList<MatchesData> MatchData;
    /*
    TODO:
        Update constructor calls in rest of code after sprint 1 presentation
        for now, going to just overload the constructor for compatability
     */
    private User() {}

    public User(String firstname, String lastname, String email, String dateofbirth, String id, HashMap<String, Dog> dogs, String contactInfo, ArrayList<MatchesData> MatchData){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dateofbirth = dateofbirth;
        this.id = id;
        this.dogs = dogs;
        //--
        this.contactInfo = contactInfo;
        this.MatchData = MatchData;
    }

    public User(String firstname, String lastname, String email, String dateofbirth, Dog dog, String contactInfo, ArrayList<MatchesData> MatchData){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dateofbirth = dateofbirth;
        this.id = "";
        this.dogs = new HashMap<String, Dog>();
        this.dogs.put(dog.getName(), dog);
        //--
        this.contactInfo = contactInfo;
        this.MatchData = MatchData;
    }

    public User(String firstname, String lastname, String email, String dateofbirth, HashMap<String, Dog> dogs, String id, String contactInfo, ArrayList<MatchesData> MatchData){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dateofbirth = dateofbirth;
        this.dogs = dogs;
        this.contactInfo = contactInfo;
        this.MatchData = MatchData;
    }

    public void setFirstName(String firstname){
        this.firstname = firstname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setDateOfBirth(String dateofbirth) { this.dateofbirth = dateofbirth; }

    public void setDogs(HashMap<String, Dog> dogs){ this.dogs = dogs; }

    public void setEmail(String email){
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    //--
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    //--
    public String getContactInfo() { return this.contactInfo; }

    public String getId() {
        return id;
    }

    public String getFirstname(){
        return this.firstname;
    }

    public String getLastname(){
        return this.lastname;
    }

    public String getEmail(){
        return this.email;
    }

    public String getDateOfBirth() { return this.dateofbirth; }

    public HashMap<String, Dog> getDogs(){ return this.dogs; }

    //--

    public void setMatchData(ArrayList<MatchesData> MatchData){ this.MatchData = MatchData; }

    public ArrayList<MatchesData> getMatchData() {return this.MatchData; }

    /**
     * This method is to turn a User class object into a json object.
     * @param
     * @return A JSONObject object representation of a User class object.
     */
    public JSONObject toJSONObject(){

        JSONObject jsonUser = new JSONObject();
        JSONArray dogList = new JSONArray();
        JSONArray matchesData = new JSONArray();
        try {
            for(Map.Entry dog : dogs.entrySet()){
                Dog tempDog = (Dog) dog.getValue();
                dogList.put(tempDog.toJSONObject());
            }
            /*for(MatchesData data : getMatchData()) {
                matchesData.put(data.getMatchedEmail());
            }*/
            //jsonUser.put("_id", getId());
            jsonUser.put("firstname", getFirstname());
            jsonUser.put("lastname", getLastname());
            jsonUser.put("email", getEmail());
            jsonUser.put("dateofbirth", getDateOfBirth());
            jsonUser.put("dogs", dogList);
            //--
            jsonUser.put("contactInfo", getContactInfo());
            //jsonUser.put("Match Data", matchesData);

        } catch (JSONException e) {

            e.printStackTrace();

        }
        return jsonUser;
    }

    public static User toUser(JSONObject jsonUser){
        User user = null;
        HashMap<String, Dog> dogs = new HashMap<String, Dog>();
        ArrayList<MatchesData> matchList = new ArrayList<MatchesData>();
        String id = "";
        try {
            //id = jsonUser.getString("_id");
            id = jsonUser.getString("email");
        } catch (JSONException e) {
            Log.e("toUser", "Could not parse user email");
        }
        try{
            for(int i = 0; i < jsonUser.getJSONArray("dogs").length(); i++){
                Dog dog;
                dog = Dog.toDog(jsonUser.getJSONArray("dogs").getJSONObject(i));
                dogs.put(dog.getName(), dog);
            }
            //*****
            /*
            *TODO: NEED REQUEST RESPONSE TO CONTAIN JSON ARRAY WITH KEY CALLED matchedList
             */
            try{
                for(int i = 0; i < jsonUser.getJSONArray("matchList").length(); i++){
                    JSONObject jsonMatch = jsonUser.getJSONArray("matchList").getJSONObject(i);
                    MatchesData match =  new MatchesData(
                            jsonMatch.getString("matchedEmail"),
                            jsonMatch.getString("matchedDogName"),
                            jsonMatch.getString("matchedContactInfo")
                    );
                    matchList.add(match);
                }
            }catch(JSONException e){
                Log.w("User.toUser", "No value for matchList");
            }
            user = new User
                    (
                            jsonUser.getString("firstname"),
                            jsonUser.getString("lastname"),
                            jsonUser.getString("email"),
                            jsonUser.getString("dateofbirth"),
                            dogs,
                            jsonUser.getString("_id"),
                            //--
                            jsonUser.getString("contactInfo"),
                            matchList

            );
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("User.toUser conversion error");
        }
        return user;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        User user = new User();
        user = (User) super.clone();
        return user;
    }

    public User getUserCopy() {
        User user = null;
        try {
            user = (User) clone();
        } catch (CloneNotSupportedException e) {
            user = new User(getFirstname(), getLastname(), getEmail(), getDateOfBirth(), getDogs(), getId(), getContactInfo(), getMatchData());
            e.printStackTrace();
        }
        user.setDogs((HashMap<String, Dog>) dogs.clone());
        return user;
    }

    @NonNull
    @Override
    public String toString() {
        return this.toJSONObject().toString();
    }


}
