package com.example.localdogs;

import java.util.List;
import java.util.ArrayList;
import com.example.localdogs.Spot;

public class Cards {
    public ArrayList<Spot> spots;
    public Cards(){
        this.spots = new ArrayList<Spot>();
        //defaultSpots();
        defaultDogs();
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
    public void newSpot(String name, String city, String url){
        this.spots.add(new Spot(name, city, url));
    }
}
