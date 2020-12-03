package com.example.localdogs;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.localdogs.ui.CardStackAdapter;
import com.example.localdogs.ui.login.LoginActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.regex.Pattern;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class RegistrationPageRegisterTest {

    @Rule
    public IntentsTestRule<RegistrationPage> registrationPageIntentsTestRule = new IntentsTestRule<RegistrationPage>(RegistrationPage.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkBoxesTest() {
        //checkbox behavior
        //if clicked, make sure it is checked
        Espresso.onView(withId(R.id.purebredCheckbox)).check(matches(isNotChecked())).perform(click());
        Espresso.onView(withId(R.id.purebredCheckbox)).check(matches(isChecked()));

        //if not clicked, make sure it is unchecked
        Espresso.onView(withId(R.id.vaccinatedCheckBox)).check(matches(isNotChecked()));

    }

    @After
    public void tearDown() throws Exception {
    }
}