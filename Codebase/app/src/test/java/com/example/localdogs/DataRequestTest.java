package com.example.localdogs;

import android.provider.ContactsContract;

import com.example.localdogs.data.DataRequests;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataRequestTest {
    @Test
    public void testHttpGetRequest(){
        DataRequests dataRequests = new DataRequests();
        dataRequests.RetrieveUserProfile("email", "dismuhemail@aol.com");
    }
}
