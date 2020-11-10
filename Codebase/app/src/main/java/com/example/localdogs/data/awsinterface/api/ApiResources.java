package com.example.localdogs.data.awsinterface.api;

public class ApiResources {
    static String updateUser = "/updateUser";
    static String updateDog = "/updateUser";
    static String uploadDog = "/updateUser";
    static String uploadUser = "/uploadUser";
    static String retrieveUser = "/retrieveUser";
    static String retrieveDogs = "/retrieveDog";

    public static String updateUser(){
        return updateUser;
    }

    public static String updateDog(){
        return updateDog;
    }

    public static String uploadDog(){
        return uploadDog;
    }

    public static String uploadUser(){
        return uploadUser;
    }

    public static String retrieveUser(){
        return retrieveUser;
    }

    public static String retrieveDogs(){
        return retrieveDogs;
    }
}
