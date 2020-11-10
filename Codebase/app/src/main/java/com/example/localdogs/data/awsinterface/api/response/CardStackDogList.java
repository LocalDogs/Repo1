package com.example.localdogs.data.awsinterface.api.response;

import android.content.Context;

import com.example.localdogs.DogFilter.DogFilterActivity;
import com.example.localdogs.data.Dog;
import com.example.localdogs.data.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class CardStackDogList {
    private static Context context;
    private static CardStackDogList instance;
    private static ArrayList<Dog> dogs;

    private CardStackDogList(Context context){
        this.context = context;
        this.dogs = null;
    }

    public synchronized static CardStackDogList getInstance(Context context){
        if(instance == null) return new CardStackDogList(context.getApplicationContext());
        return instance;
    }

    public synchronized void setDogs(DogFilterResult dogs){
        this.dogs = new ArrayList<Dog>();
        for(User u : dogs.getDogs()){
            for(Map.Entry dog : u.getDogs().entrySet()){
                Dog tempDog = (Dog) dog.getValue();
                this.dogs.add(tempDog);
            }
        }
    }

    public ArrayList<Dog> getDogs(){
        return dogs;
    }

}
