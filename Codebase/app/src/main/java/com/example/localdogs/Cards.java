package com.example.localdogs;

import java.util.List;
import java.util.ArrayList;
import com.example.localdogs.Spot;

public class Cards {
    ArrayList<Spot> spots;
    public Cards(){
        this.spots = new ArrayList<Spot>();
        defaultSpots();
        //defaultDogs();
    }

    public void emptyCards(){
        this.spots = new ArrayList<Spot>();
    }
    public void defaultSpots()
    {
        spots.add(new Spot("Yasaka Shrine", "Kyoto", "https://source.unsplash.com/Xq1ntWruZQI/600x800"));
        spots.add(new Spot("Fushimi Inari Shrine", "Kyoto", "https://source.unsplash.com/NYyCqdBOKwc/600x800"));
        spots.add(new Spot("Bamboo Forest", "Kyoto", "https://source.unsplash.com/buF62ewDLcQ/600x800"));
        spots.add(new Spot("Brooklyn Bridge", "New York", "https://source.unsplash.com/THozNzxEP3g/600x800"));
        spots.add(new Spot("Empire State Building", "New York", "https://source.unsplash.com/USrZRcRS2Lw/600x800"));
        spots.add(new Spot("The statue of Liberty", "New York", "https://source.unsplash.com/PeFk7fzxTdk/600x800"));
        spots.add(new Spot("Louvre Museum", "Paris", "https://source.unsplash.com/LrMWHKqilUw/600x800"));
        spots.add(new Spot("Eiffel Tower", "Paris", "https://source.unsplash.com/HN-5Z6AmxrM/600x800"));
        spots.add(new Spot("Big Ben", "London", "https://source.unsplash.com/CdVAUADdqEc/600x800"));
        spots.add(new Spot("Great Wall of China", "China", "https://source.unsplash.com/AWh9C-QjhE4/600x800"));
    }
    public void defaultDogs(){
        spots.add(new Spot("Monster", "Golden Retriever", "https://i.imgur.com/Zl1eL2d.jpg"));
        spots.add(new Spot("Max", "Pitbull Mix", "https://i.imgur.com/E0MbHhU.jpg"));
        spots.add(new Spot("Shep", "Border Collie", "https://i.imgur.com/7mFNyBt.jpg"));
        spots.add(new Spot("Lola", "French Bulldog", "https://i.imgur.com/4aP8WFJ.jpg"));
        spots.add(new Spot("Chester", "German Shephard", "https://i.imgur.com/v3s7AE8.jpg"));
        spots.add(new Spot("S P A W N", "Labrador", "https://source.unsplash.com/N2ARFd1jAiw/600x800"));
    }
}
