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
     * NOTE: YOU WILL NOT BE ABLE TO MODIFY THE USER OBJECT INSTANCE FROM HERE;
     * TO DO THAT, YOU WILL HAVE TO CONSTRUCT A NEW USER OBJECT AND PASS IT INTO
     * AUTHENTICATION.UPDATECURRENTSESSION()
     * NOTE: DO NOT USE THIS TO STORE SOME OTHER USER'S PROFILE *THAT IS NOT THE
     * SESSION HOLDER'S*; THAT WOULDN'T EVEN MAKE SENSE. IF YOU WANT TO DO THAT, OR STORE
     * SOME PROFILE DATA FOR SOME OTHER REASON, USE A USER OBJECT THAT IS NOT GLOBALLY
     * ACCESSIBLE. STORE ONLY FINAL CHANGES TO THE CURRENT SESSION HOLDER'S USER PROFILE HERE
     * ****AFTER A SUCCESSFUL POST TO THE DATABASE WITH THE PROFILE UPDATE******
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

    protected void updateCurrentSessionUser(User user){
        currentSessionUser = user;
    }

}
