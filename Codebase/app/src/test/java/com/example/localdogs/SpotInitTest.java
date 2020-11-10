package com.example.localdogs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SpotInitTest {
    private Spot s;
    private String name;
    private String city;
    private String url;
    private int id;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void spotConstructorTest() {
        s = new Spot();
        assertNull(s.name);
        assertNull(s.city);
        assertNull(s.url);
        assertNotNull(s.id);

    }
    @After
    public void tearDown() throws Exception {
    }
}