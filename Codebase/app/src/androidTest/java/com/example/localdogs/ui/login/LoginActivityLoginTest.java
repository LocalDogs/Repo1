package com.example.localdogs.ui.login;

import android.util.Log;
import android.view.KeyEvent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.rule.ActivityTestRule;

import com.example.localdogs.Cards;
import com.example.localdogs.Cardstack;
import com.example.localdogs.R;
import com.example.localdogs.TermsOfUse;
import com.example.localdogs.ui.CardStackAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class LoginActivityLoginTest {

    @Rule
    public IntentsTestRule<LoginActivity> mLoginActivityActivityTestRule = new IntentsTestRule<LoginActivity>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void login() {
        //login action
        Espresso.onView(withId(R.id.username)).perform(typeText("bobby@gmail.com"));
        Espresso.onView(withId(R.id.username)).perform(pressKey(KeyEvent.KEYCODE_MENU));
        Espresso.onView(withId(R.id.password)).perform(typeText("remember!@#$1234"));
        Espresso.onView(withId(R.id.password)).perform(pressKey(KeyEvent.KEYCODE_MENU));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.login)).perform(click());

        /*
        *TODO: FIX THIS TEST CASE, AS IT HAS ISSUES PROCESSING KOTLIN CLASS
         */
        intended(hasComponent(Cardstack.class.getName()));

    }

    @Test
    public void register() {
        //register action
        Espresso.onView(withId(R.id.register)).perform(click());

        intended(hasComponent(TermsOfUse.class.getName()));

    }

    @After
    public void tearDown() throws Exception {
    }
}