package com.example.localdogs.data.awsinterface;

import android.content.Context;

import com.example.localdogs.data.User;

class CurrentSession {
    private static User currentSessionUser;
    private static Context context;
    private static CurrentSession instance;

    private CurrentSession(Context context){
        this.context = context;
        this.instance = this;
    }
    /**
     * NOTE: THE GETTERS IN THIS CLASS CAN RETURN NULL
     * IF YOU'RE IN THE CARDSTACK, OR A SUCCESS CALLBACK FROM SIGNING IN,
     * IT SHOULD BE SAFE, BUT IF ITS NOT, THAT'S ON YOU, SO CHECK IT
     */
    protected static synchronized CurrentSession getInstance(Context context){
        if(instance == null) instance = new CurrentSession(context.getApplicationContext());
        return instance;
    }

    protected String getCurrentSessionUserEmail(){
        if(currentSessionUser == null) return null;
        return currentSessionUser.getEmail();
    }

    protected String getCurrentSessionUserFirstName(){
        if(currentSessionUser == null) return null;
        return currentSessionUser.getFirstname();
    }

    protected String getCurrentSessionUserLastName(){
        if(currentSessionUser == null) return null;
        return currentSessionUser.getLastname();
    }

    protected String getCurrentSessionUserDateOfBirth(){
        if(currentSessionUser == null) return null;
        return currentSessionUser.getDateOfBirth();
    }

    protected String getCurrentSessionUser(){
        if(currentSessionUser == null) return null;
        return currentSessionUser.getDateOfBirth();
    }

    protected void updateCurrentSessionUser(User user){
        currentSessionUser = user;
    }

}
