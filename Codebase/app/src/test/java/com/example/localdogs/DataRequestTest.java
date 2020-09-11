package com.example.localdogs;

import android.provider.ContactsContract;

import com.example.localdogs.data.DataRequests;
import com.example.localdogs.data.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataRequestTest {
    @Test
    public void testHttpsGetRequest(){
        DataRequests dataRequests = new DataRequests();
        User[] user = User.deserializeFromJson(dataRequests.retrieveUserInfo("email", "heehaw@aol.com"));
        for(User u : user){
            System.out.println(u);
        }
    }
    @Test
    public void testHttpsPostRequest(){
        DataRequests dataRequests = new DataRequests();
        User user = new User("Randy", "Savage", "heehaw@aol.com", "05/02/1969");
        String response = dataRequests.sendNewUserInfo(user.serializeToJson());
        System.out.println(response);
    }
}
