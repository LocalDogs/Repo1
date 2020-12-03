package com.example.localdogs

import android.content.Intent
import android.view.Gravity
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.localdogs.ui.CardStackAdapter
import com.example.localdogs.ui.login.LoginActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CardstackNavDrawerTest {
    @Rule
    @JvmField
    val mainActivityRule: IntentsTestRule<Cardstack> = IntentsTestRule<Cardstack>(Cardstack::class.java)

    @Before
    fun setUp() {
    }

    @Test
    fun openCloseDrawerTest() {
        //open drawer
        Espresso.onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        //check if drawer view is OPEN
        Espresso.onView(withId(R.id.drawer_layout))
                .check(matches(isOpen(Gravity.LEFT)))

        //close drawer
        Espresso.onView(withId(R.id.drawer_layout))
                .check(matches(isOpen(Gravity.LEFT)))
                .perform(DrawerActions.close());

        //check if drawer view is CLOSED
        Espresso.onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))

    }
    @Test
    fun navDrawerActivityTest() {
        //launch map activity from drawer
        Espresso.onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        Espresso.onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_map))

        //check the activity that is launched
        Intents.intended(hasComponent(MapsActivity::class.java.name))

    }

    @After
    fun tearDown() {
    }
}