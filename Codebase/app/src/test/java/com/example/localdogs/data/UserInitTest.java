package com.example.localdogs.data;

import com.example.localdogs.dob;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserInitTest {
    private User u;
    private Dog a, b, c, d;


    @Before
    public void setUp() throws Exception {
    }

    @Before
    public void initializeInfoTest() {
        u = new User("Jane","Doe", "jane@gmail.com", "01/01/1980", "", new HashMap<String, Dog>(1, 1), "Email: jane@gmail.com, Twitter: @jane_atgmail");
    }

    @Test
    public void setInfoTest() {
        assertNotNull(u);
    }

    @Test
    public void assertInfoTest() {
        assertNotNull(u);
    }

    @Test
    public void getFirstNameTest() {
        assert(u.getFirstname().equals("Jane"));
    }

    @Test
    public void getLastNameTest(){ assert(u.getLastname().equals("Doe")); }

    @Test
    public void getEmailTest() { assert(u.getEmail().equals("jane@gmail.com")); }

    @Test
    public void getDateOfBirthTest() { assert(u.getDateOfBirth().equals("01/01/1980")); }

    @Test
    public void getIdTest() { assert(u.getId().equals("")); }

    @Test
    public void getDogMapTest() {
        //HashMap<k, v>
        a = new Dog("jane@gmail.com","Sparky", new ArrayList<String>(Arrays.asList("Beagle")), new dob(12,31,2005), 35, 5);
        b = new Dog("jane@gmail.com","Foxy", new ArrayList<String>(Arrays.asList("German Shepherd", "Malamute")), new dob(11,05,2009), 80, 4);
        c = new Dog("jane@gmail.com","AAAAA", new ArrayList<String>(Arrays.asList("Dalmation")), new dob(01,01,2019), 67, 1);

        HashMap<String, Dog> map = new HashMap<>();
        map.put("j", a);
        map.put("c", b);
        map.put("p", c);

        HashMap<String, Dog> expected = new HashMap<>();
        expected.put("c", b);
        expected.put("j", a);
        expected.put("p", c);

        //All passed / true

        //1. Test equal, ignore order
        assertThat(map, is(expected));

        //2. Test size
        assertThat(map.size(), is(3));

        //3. Test map entries
        assertEquals(a, map.get("j"));

        assertNotEquals(b, map.get("d"));

        //4. Test map key
        assertFalse(map.containsKey("q"));
        assertTrue(map.containsKey("p"));

        //5. Test map value
        assertFalse(map.containsValue(d));
        assertTrue(map.containsValue(b));
    }

    @Test
    public void getContactInfoTest() { assert(u.getContactInfo().equals("Email: jane@gmail.com, Twitter: @jane_atgmail")); }


    @After
    public void tearDown() throws Exception {
    }
}