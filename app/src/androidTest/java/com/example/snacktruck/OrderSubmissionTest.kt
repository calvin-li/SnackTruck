package com.example.snacktruck


import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.junit.Before

@LargeTest
@RunWith(AndroidJUnit4::class)
class OrderSubmissionTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var checkBox: ViewInteraction
    private lateinit var submitButton: ViewInteraction
    private lateinit var orderedItem: ViewInteraction

    @Before
    fun setup() {
        checkBox = onView(
            allOf(
                withId(R.id.snack_checkbox),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.snackList),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )

        submitButton = onView(
            allOf(
                withId(R.id.submitButton), withText("Submit Order"),
                childAtPosition(
                    allOf(
                        withId(R.id.main_container),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )

        orderedItem = onView(
            allOf(
                withId(R.id.order_list),
                withText("French fries")
            )
        )
    }

    @Test
    fun emptyOrderSubmissionTest() {
        submitButton.perform(click())
        orderedItem.check(doesNotExist())
    }

    @Test
    fun orderSubmissionTest(){
        checkBox.perform(click())
        submitButton.perform((click()))
        orderedItem.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
