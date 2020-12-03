package com.example.localdogs;

import android.view.KeyEvent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.localdogs.ui.CardStackAdapter;
import com.example.localdogs.ui.login.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class RegistrationPageRegisterTest {

    @Rule
    public IntentsTestRule<RegistrationPage> registrationPageIntentsTestRule = new IntentsTestRule<RegistrationPage>(RegistrationPage.class);


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void nameFields() {
        //login action
        Espresso.onView(withId(R.id.firstNameEditText)).perform(typeText("Bobby"));
        Espresso.onView(withId(R.id.firstNameEditText)).perform(pressKey(KeyEvent.KEYCODE_MENU));
        Espresso.onView(withId(R.id.lastNameEditText)).perform(typeText("Hill"));
        Espresso.onView(withId(R.id.lastNameEditText)).perform(pressKey(KeyEvent.KEYCODE_MENU));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.login)).perform(click());

        intended(hasComponent(CardStackAdapter.class.getName()));

    }

    @After
    public void tearDown() throws Exception {
    }
}