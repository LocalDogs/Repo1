package com.example.localdogs.data;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class User {
    private String firstname;
    private String lastname;
    private String email;
    private String dateofbirth;
    private ArrayList<Dog> dogs;
    /*
    TODO:
        Update constructor calls in rest of code after sprint 1 presentation
        for now, going to just overload the constructor for compatability
     */
    public User(String firstname, String lastname, String email, String dateofbirth){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dateofbirth = dateofbirth;
        ArrayList<String> breedList = new ArrayList<String>();
        breedList.add("pickle");
        breedList.add("rick");
        ArrayList<Dog> dogList = new ArrayList<Dog>();
        dogList.add(new Dog(email, "Cheerios", breedList, 3, 50, 2));
        this.dogs = dogList;
    }

    public User(String firstname, String lastname, String email, String dateofbirth, ArrayList<Dog> dogs){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dateofbirth = dateofbirth;
        this.dogs = dogs;
    }

    public void setFirstName(String firstname){
        this.firstname = firstname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setDateOfBirth(String dateofbirth) { this.dateofbirth = dateofbirth; }

    public void setDogs(ArrayList<Dog> dogs){ this.dogs = dogs; }

    public void setEmail(String email){
        this.email = email;
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

    public ArrayList<Dog> getDogs(){ return this.dogs; }

    /**
     * This method is to turn a User class object into a json object.
     * @param
     * @return A JSONObject object representation of a User class object.
     */
    public JSONObject toJSONObject(){

        JSONObject jsonUser = new JSONObject();
        JSONArray dogList = new JSONArray();
        try {
            for(Dog dog : dogs){
                dogList.put(dog.toJSONObject());
            }
            jsonUser.put("firstname", getFirstname());
            jsonUser.put("lastname", getLastname());
            jsonUser.put("email", getEmail());
            jsonUser.put("dateofbirth", getDateOfBirth());
            jsonUser.put("dogs", dogList);

        } catch (JSONException e) {

            e.printStackTrace();

        }
        return jsonUser;
    }

    public static User toUser(JSONObject jsonUser){
        User user = null;
        ArrayList<Dog> dogs = new ArrayList<Dog>();
        try {
            /*
                TODO:
                    uncomment out the following code when temporary constructor is removed
            */
            /*for(int i = 0; i < jsonUser.getJSONArray("dogs").length(); i++){
                Dog dog = Dog.toDog(jsonUser.getJSONArray("dogs").getJSONObject(i));
                dogs.add(dog);
            }*/
            user = new User
                    (
                            jsonUser.getString("firstname"),
                            jsonUser.getString("lastname"),
                            jsonUser.getString("email"),
                            jsonUser.getString("dateofbirth")
                            //dogs
            );

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("User.toUser conversion error");
        }
        return user;
    }

    @NonNull
    @Override
    public String toString() {
        return this.toJSONObject().toString();
    }


}
