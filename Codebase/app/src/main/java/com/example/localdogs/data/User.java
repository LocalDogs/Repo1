package com.example.localdogs.data;

import androidx.annotation.NonNull;

public class User {
    private String firstname;
    private String lastname;
    private String email;
    private String dateofbirth;

    public User(String firstname, String lastname, String email, String dateofbirth){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dateofbirth = dateofbirth;
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

    @NonNull
    @Override
    public String toString() {
        return "User: { firstname: " + this.firstname + " lastname: " + this.lastname + " email: " + this.email + " }";
    }


}
