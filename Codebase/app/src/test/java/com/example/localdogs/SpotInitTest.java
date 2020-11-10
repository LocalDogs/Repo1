package com.example.localdogs;

import com.example.localdogs.data.Dog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SpotInitTest {
    private Spot s;
    private Dog d;

    @Before
    public void setUp() throws Exception {
    }

    @Before
    public void spotConstructorInitializationTest() {
        d = new Dog("Jane","Max", new ArrayList<String>(Arrays.asList("Pitbull", "Staffie")), new dob(12,31,2008), 50, 2, "https://i.imgur.com/E0MbHhU.jpg");
        s = new Spot(d);
    }

    @Test
    public void spotConstructorTest() {
        assertNotNull(d);
        assertNotNull(s);
    }

    @Test
    public void getNameTest(){
        assert(s.getName().equals("Max"));
    }

    @After
    public void tearDown() throws Exception {
    }
}