package com.example.localdogs.DogFilter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DogFilter implements Parcelable {

    private static DogFilter instance;
    private static Context ctx;
    private int minACL;
    private int maxACL;
    private int minWeight;
    private int maxWeight;
    private int minAge;
    private int maxAge;
    private String breed1;
    private String breed2;

    private DogFilter(Context ctx) {
        this.ctx = ctx;
    }

    protected DogFilter(Parcel in) {
        minACL = in.readInt();
        maxACL = in.readInt();
        minWeight = in.readInt();
        maxWeight = in.readInt();
        minAge = in.readInt();
        maxAge = in.readInt();
        breed1 = in.readString();
        breed2 = in.readString();
    }

    public static final Creator<DogFilter> CREATOR = new Creator<DogFilter>() {
        @Override
        public DogFilter createFromParcel(Parcel in) {
            return new DogFilter(in);
        }

        @Override
        public DogFilter[] newArray(int size) {
            return new DogFilter[size];
        }
    };

    public static synchronized DogFilter getInstance(Context ctx) {
        if (instance == null) instance = new DogFilter(ctx.getApplicationContext());
        return instance;
    }

    public synchronized void setMinAcl(int minAcl) {
        this.minACL = minAcl;
    }

    public synchronized void setMaxACL(int maxACL) {
        this.maxACL = maxACL;
    }

    public synchronized void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public synchronized void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public synchronized void setMinWeight(int minWeight) {
        this.minWeight = minWeight;
    }

    public synchronized void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public synchronized void setBreed1(String breed1) {
        this.breed1 = breed1;
    }

    public synchronized void setBreed2(String breed2) {
        this.breed2 = breed2;
    }

    public int getMinACL() {
        return minACL;
    }

    public int getMaxACL() {
        return maxACL;
    }

    public int getMinWeight() {
        return minWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public String getBreed1() {
        return breed1;
    }

    public String getBreed2() {
        return breed2;
    }

    @NonNull
    @Override
    public synchronized String toString() {

        return this.toJSONObject().toString();

    }

    public synchronized JSONObject toJSONObject() {

        JSONObject jsonDogFilter = new JSONObject();
        try {

            jsonDogFilter.put("minACL", minACL);
            jsonDogFilter.put("maxACL", maxACL);
            jsonDogFilter.put("minWeight", minWeight);
            jsonDogFilter.put("maxWeight", maxWeight);
            jsonDogFilter.put("minAge", minAge); // temporary
            jsonDogFilter.put("MaxAge", maxAge);
            jsonDogFilter.put("breed1", breed1);
            jsonDogFilter.put("breed2", breed2);

        } catch (JSONException e) {

            e.printStackTrace();

        }
        return jsonDogFilter;
    }

    public synchronized HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("minACL", String.valueOf(minACL));
        hashMap.put("maxACL", String.valueOf(maxACL));
        hashMap.put("minWeight", String.valueOf(minWeight));
        hashMap.put("maxWeight", String.valueOf(maxWeight));
        hashMap.put("minAge", String.valueOf(minAge));
        hashMap.put("maxAge", String.valueOf(maxAge));
        hashMap.put("breed1", String.valueOf(breed1));
        hashMap.put("breed2", String.valueOf(breed2));
        return hashMap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.i("DogFilter", "Parceling | minWeight: " + minWeight + ", maxWeight: " + maxWeight);
        dest.writeInt(minACL);
        dest.writeInt(maxACL);
        dest.writeInt(minWeight);
        dest.writeInt(maxWeight);
        dest.writeInt(minAge);
        dest.writeInt(maxAge);
        dest.writeString(breed1);
        dest.writeString(breed2);
    }
}
