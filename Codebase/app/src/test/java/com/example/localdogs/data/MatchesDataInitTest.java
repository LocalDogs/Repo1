package com.example.localdogs.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatchesDataInitTest {
    private MatchesData m;

    @Before
    public void setUp() throws Exception {
    }

    @Before
    public void initializeInfoTest() {
        m = new MatchesData("Joe@gmail.com", "Joe", "Spot", "Email: Joe@gmail.com, Twitter: JoeMan1234");
    }

    @Test
    public void getMatchedEmailTest() {
        assert(m.getMatchedEmail().equals("Joe@gmail.com"));
    }

    @Test
    public void getMatchedFirstNameTest() {
        assert(m.getMatchedFirstName().equals("Joe"));
    }

    @Test
    public void getMatchedDogTest() {
        assert(m.getMatchedDogName().equals("Spot"));
    }

    @Test
    public void getMatchedContactInfoTest() {
        assert(m.getMatchedContactInfo().equals("Email: Joe@gmail.com, Twitter: JoeMan1234"));
    }

    @After
    public void tearDown() throws Exception {
    }
}