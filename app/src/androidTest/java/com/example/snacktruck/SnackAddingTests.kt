package com.example.snacktruck

import android.widget.Button
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

import org.hamcrest.core.AllOf.allOf

import org.junit.Rule
import org.junit.Test

@LargeTest
class SnackAddingTests {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addDialogTest(){
        addNewSnack(confirm = false)

        // Verify elements of snack adding dialog
        val dialogRootMatcher = withId(
                R.id.action_bar_root
        )
        onView(dialogRootMatcher).check(matches(isDisplayed()))

        listOf(
            withText("Add New Item"),
            withId(R.id.add_snack_container),
            allOf(
                instanceOf(Button::class.java),
                withText("Cancel")
            ),
            allOf(
                instanceOf(Button::class.java),
                withText("Confirm")
            )
        ).map {
            onView(it).check(matches(
                isDescendantOfA(dialogRootMatcher)
            ))
        }
    }

    @Test
    fun cancelTest(){
        val testSnackName = "testSnack"
        addNewSnack(testSnackName, confirm = false)

        onView(
            allOf(
                instanceOf(Button::class.java),
                withText("Cancel")
            )
        ).perform(click())

        onView(
            allOf(
                withId(R.id.snack_name),
                withText(testSnackName),
                isDisplayed()
            )
        ).check(doesNotExist())
    }

    @Test
    fun addSnackTest(){
        val testSnackName = "TestSnack"
        addNewSnack(testSnackName)

        onView(
            allOf(
                withId(R.id.snack_name),
                withText(testSnackName),
                hasTextColor(R.color.veggie),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun addTwoSnacksTest(){
        val testSnackName = "TestSnack"
        val testSnackName2 = "TestSnack2"
        addNewSnack(testSnackName)
        addNewSnack(testSnackName2, category = Snack.NonVeggie)

        checkSnackItem(testSnackName, R.color.veggie)
        checkSnackItem(testSnackName2, R.color.nonVeggie)
    }

    private fun checkSnackItem(snackName: String, textColor: Int) {
        onData(
            snackMatch(`is`(snackName))
        ).check(
            matches(
                withChild(
                    allOf(
                        withId(R.id.snack_name),
                        withText(snackName),
                        hasTextColor(textColor),
                        isDisplayed()
                    )
                )
            )
        )
    }

    private fun addNewSnack(
        newSnackName: String = "",
        confirm: Boolean = true,
        category: String = Snack.Veggie) {
        onView(
            withId(R.id.action_add)
        ).perform(click())

        if (newSnackName != "") {
            onView(withId(R.id.add_snack_edittext)).perform(replaceText(newSnackName))
        }

        if (category == Snack.NonVeggie){
            val categorySpinner = withId(R.id.category_chooser)
            onView(categorySpinner).perform(click())
            onView(
                withText(Snack.NonVeggie)
            )
            .inRoot(isPlatformPopup())
            .perform(click())}

        if (confirm){
            onView(
                allOf(
                    instanceOf(Button::class.java),
                    withText("Confirm")
                )
            ).perform(click())
        }
    }

    fun snackMatch(nameMatcher: Matcher<String>): Matcher<Snack>{
        return object: TypeSafeMatcher<Snack>(){
            override fun describeTo(description: Description?) {
                description?.appendText("Matches Snack objects")
            }

            override fun matchesSafely(snack: Snack?): Boolean {
                return nameMatcher.matches(snack?.name)
            }
        }
    }
}