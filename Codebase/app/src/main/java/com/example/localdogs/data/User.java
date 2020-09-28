package com.example.localdogs.data;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String firstname;
    private String lastname;
    private String email;
    private String dateofbirth;
    private String password;

    public User(String firstname, String lastname, String email, String dateofbirth, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dateofbirth = dateofbirth;
        this.password = password;
    }

    public void setFirstName(String firstname){
        this.firstname = firstname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setDateOfBirth(String dateofbirth) { this.dateofbirth = dateofbirth; }

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

    public String getPassword() { return this.password; }

    public JSONObject toJSONObject(){
        JSONObject jsonUser = new JSONObject();
        try {
            jsonUser.put("firstname", getFirstname());
            jsonUser.put("lastname", getLastname());
            jsonUser.put("email", getEmail());
            jsonUser.put("password", getPassword()); // temporary
            jsonUser.put("dateofbirth", getDateOfBirth());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonUser;
    }

    public static User toUser(JSONObject jsonUser){
        User user = null;
        try {
            user = new User
                    (
                            jsonUser.getString("firstname"),
                            jsonUser.getString("lastname"),
                            jsonUser.getString("email"),
                            jsonUser.getString("dateofbirth"),
                            jsonUser.getString("password")
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
        return "User: { firstname: " + this.firstname + " lastname: " + this.lastname + "dateofbirth: " + this.getDateOfBirth() + " email: " + this.email + " }";
    }


}
