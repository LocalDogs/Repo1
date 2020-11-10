package com.example.localdogs.data;

import com.example.localdogs.dob;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DogInitTest {

    private Dog d;

    @Before
    public void setUp() throws Exception {
    }

    @Before
    public void initializeInfoTest() {
        d = new Dog("Jane","Max", new ArrayList<String>(Arrays.asList("Pitbull", "Staffie")), new dob(12,31,2008), 50, 3, "https://i.imgur.com/E0MbHhU.jpg");
    }

    @Test
    public void setInfoTest() {
        assertNotNull(d);
    }

    @Test
    public void assertInfoTest() {
        assertNotNull(d);
    }

    @Test
    public void getOwnerTest() {
        assert(d.getOwner().equals("Jane"));
    }

    @Test
    public void getNameTest(){
        assert(d.getName().equals("Max"));
    }

    @Test
    public void getBreedsListTest() {
        assert(d.getBreeds().get(0).equals("Pitbull"));
        assert(d.getBreeds().get(1).equals("Staffie"));
    }

    @Test
    public void getDOBTest() {
        assertNotNull(d.getAge());
    }

    @Test
    public void getActivityLevelTest() {
        assertEquals(3, d.getActivityLevel());
    }

    @Test
    public void getURLTest() {
        assert(d.getImgUrl().equals("https://i.imgur.com/E0MbHhU.jpg"));
    }

    @After
    public void tearDown() throws Exception {

    }
}