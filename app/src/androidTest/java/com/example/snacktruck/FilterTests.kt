package com.example.snacktruck

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import java.util.regex.Matcher

@LargeTest
class FilterTests{
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    private val veggieSnacks = listOf(
        "French fries",
        "Veggieburger",
        "Carrots",
        "Apple",
        "Banana",
        "Milkshake")
    private val nonVeggieSnacks = listOf("Cheeseburger", "Hamburger", "Hot dog")

    @Test
    fun noFiltersTest(){
        checkSnackItems(veggieSnacks)
        checkSnackItems(nonVeggieSnacks)
    }

    @Test
    fun veggieFilterTest() {
        onView(withId(R.id.nonveggie_checkbox)).perform(click())
        checkSnackItems(veggieSnacks)
        checkSnackItems(nonVeggieSnacks, doesNotExist())
    }

    @Test
    fun nonVeggieFilterTest() {
        onView(withId(R.id.veggie_checkbox)).perform(click())
        checkSnackItems(nonVeggieSnacks)
        checkSnackItems(veggieSnacks, doesNotExist())
    }

    @Test
    fun everythingFilteredTest() {
        onView(withId(R.id.veggie_checkbox)).perform(click())
        onView(withId(R.id.nonveggie_checkbox)).perform(click())

        checkSnackItems(veggieSnacks, doesNotExist())
        checkSnackItems(nonVeggieSnacks, doesNotExist())
    }

    private fun checkSnackItems(
        snacks: List<String>, viewAssert: ViewAssertion = matches(isDisplayed())) {
        snacks.map {
            onView(
                allOf(
                    withId(R.id.snack_name),
                    withText(it),
                    isDisplayed()
                )
            ).check(viewAssert)
        }
    }
}