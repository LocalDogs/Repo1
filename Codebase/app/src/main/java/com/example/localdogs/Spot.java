package com.example.localdogs;

import com.example.localdogs.data.Dog;

public class Spot {
    public String name;
    public String city;
    public String url;
    public int id;

    public Dog dog;

    public Spot() {

    }
    public Spot(Dog dog){
        this.dog = dog;
        this.url = dog.getImgUrl();
        getTagline();
        getName();
    }

    public Spot(String name, String city, String url) {
        this.name = name;
        this.city = city;
        this.url = url;
    }

    public String getName(){
        name = this.dog.getName();
        return this.dog.getName();
    }
    public String getTagline(){
        city = this.dog.getBreedsAsString() + " | " +  this.dog.getAge() + " years" + " | " + this.dog.getWeight() + " lbs | Energy Level: " + this.dog.getActivityLevelAsString();
        return city;
    }
}
