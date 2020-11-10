package com.example.localdogs.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DogInitTest {

    private Dog d;

    @Before
    public void setUp() throws Exception {
    }

    //@Test
    //public void DogConstructorTest() {
        //d = new Dog();
    //}

    @Test
    public void SetInfoTest() {
        d.setName("Sparky");
        assert(d.toString().equals("Sparky"));

        d.setOwner("rememberthis@remember.com");
        assert(d.toString().equals("rememberthis@remember.com"));

        //***************RIP
        d.setBreeds("German Shepherd");
        assert(d.toString().equals("German Shepherd"));

        //**********RIP
        d.setAge("01/01/2015");

        d.setWeight(80);
        assertEquals(80, d.getWeight());

        d.setActivityLevel(3);
        assertEquals(3, d.getActivityLevel());

        d.setActivityLevel(3);
        assertEquals(3, d.getActivityLevel());

        d.setImgUrl("www.hello.com");
        assert(d.toString().equals(d.getImgUrl()));

    }

    @Test
    public Boolean isMixedTest() {

    }

    @After
    public void tearDown() throws Exception {
    }
}