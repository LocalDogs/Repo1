package com.example.localdogs;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import com.example.localdogs.Spot;
import com.example.localdogs.data.Dog;

public class Cards {
    public ArrayList<Spot> spots;
    public Cards(){
        this.spots = new ArrayList<Spot>();
        //defaultSpots();
        defaultDogs2();
    }

    public void emptyCards(){
        this.spots = new ArrayList<Spot>();
    }
    public void defaultDogs(){
        spots.add(new Spot("Monster", "Golden Retriever", "https://i.imgur.com/Zl1eL2d.jpg"));
        spots.add(new Spot("Max", "Pitbull Mix", "https://i.imgur.com/E0MbHhU.jpg"));
        spots.add(new Spot("Shep", "Border Collie", "https://i.imgur.com/7mFNyBt.jpg"));
        spots.add(new Spot("Lola", "French Bulldog", "https://i.imgur.com/4aP8WFJ.jpg"));
        spots.add(new Spot("Chester", "German Shepard", "https://i.imgur.com/v3s7AE8.jpg"));
        spots.add(new Spot("Beast", "Great Dane", "https://i.imgur.com/r6DZC8E.jpg"));
        spots.add(new Spot("Lady", "Not sure", "https://i.imgur.com/k6AV99J.jpg"));
        spots.add(new Spot("Goggles", "English Shepard", "https://i.imgur.com/e1Ng7zo.jpg"));
        spots.add(new Spot("Scrungus", "Beagle", "https://i.imgur.com/eMKRlnv.jpg"));
    }
    public void defaultDogs2(){
        spots.add(new Spot(new Dog("Amelia","Monster", new ArrayList<String>(Arrays.asList("Golden Retriever")), new dob(8,15,2010), 62, 7, "https://localdogsdogimgs.s3.us-east-2.amazonaws.com/5fa056df4a33c10008492473/monster.jpg")));
        spots.add(new Spot(new Dog("Jane","Max", new ArrayList<String>(Arrays.asList("Pitbull", "Staffie")), new dob(12,31,2008), 50, 2, "https://i.imgur.com/E0MbHhU.jpg")));
    }
    public void newSpot(String name, String city, String url){
        this.spots.add(new Spot(name, city, url));
    }
}
