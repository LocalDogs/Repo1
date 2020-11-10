package com.example.localdogs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class dobInitTest {
    private dob d;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void dobConstructorTest() {
        d = new dob();
        assertNotNull(d);
    }
    @Test
    public void dateSetTest(){
        d.setMonth(5);
        assertEquals(5, d.getMonth());

        d.setDay(10);
        assertEquals(10, d.getDay());

        d.setYear(1990);
        assertEquals(1990, d.getYear());
    }
    @Test
    public void ageCalcTest(){
        // THIS TEST WILL EVENTUALLY BREAK GIVEN THAT THE DOG WILL AGE AND THE "30" WILL NOT CHANGE
        assertEquals(30, d.getAgeYears());
    }
    @Test
    public void ageDistTest(){
        assertEquals(5, d.getDiffYears(new dob(5, 10, 1995)));
    }
    @Test
    public void toStringTest(){
        assert(d.toString().equals("5/10/1990"));
    }
    @After
    public void tearDown() throws Exception {
    }
}