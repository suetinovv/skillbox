package com.example.weather

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testBottomNavigation() {
        onView(withId(R.id.nav_view))
            .check(matches(isDisplayed()))
            .check(matches(hasDescendant(withText("Поиск"))))
            .check(matches(hasDescendant(withText("Города"))))

        onView(withId(R.id.navigation_search))
            .perform(click())

        onView(withId(R.id.navigation_search))
            .check(matches(isDisplayed()))

        onView(withId(R.id.navigation_cities))
            .perform(click())

        onView(withId(R.id.navigation_cities))
            .check(matches(isDisplayed()))
    }
}
