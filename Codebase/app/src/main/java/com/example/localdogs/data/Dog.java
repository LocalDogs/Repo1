package com.example.localdogs.data;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class Dog {

    private String name;
    private String breed;
    private String dateofbirth;
    private int weight;

    Dog (String name, String breed, String dateofbirth, int weight){
        this.name = name;
        this.breed = breed;
        this.dateofbirth = dateofbirth;
        this.weight = weight;
    }

    public void setName(String name) { this.name = name; }
    public void setBreed(String breed) { this.breed = breed; }
    public void setDateOfBirth(String dateofbirth) { this.dateofbirth = dateofbirth; }
    public void setWeight(int weight) { this.weight = weight; }

    public String getName() { return this.name; }
    public String getBreed() { return this.breed; }
    public String getDateOfBirth() { return this.dateofbirth; }
    public int getWeight() { return this.weight; }

    public String serializeToJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Dog deserializeFromJson(String jsonDog){
        Gson gson = new Gson();
        return gson.fromJson(jsonDog, Dog.class);
    }

    @NonNull
    @Override
    public String toString(){
        return "Dog: { name: " + this.name + " breed: " + this.breed + " dateofbirth: " + this.dateofbirth + " weight: " + this.weight + " }";
    }

}
