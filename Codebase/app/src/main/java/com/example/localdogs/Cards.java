package com.example.localdogs;

import java.util.List;
import java.util.ArrayList;
import com.example.localdogs.Spot;

public class Cards {
    ArrayList<Spot> spots;
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
        spots.add(new Spot("S P A W N", "Labrador", "https://source.unsplash.com/N2ARFd1jAiw/600x800"));
    }
}
