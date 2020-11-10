package com.example.localdogs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class dobInitTest {
    private dob d;
    private int month;
    private int day;
    private int year;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void dobConstructorTest() {
        d = new dob();
        assertNotNull(d.month);
        assertNotNull(d.day);
        assertNotNull(d.year);
    }
    @After
    public void tearDown() throws Exception {
    }
}