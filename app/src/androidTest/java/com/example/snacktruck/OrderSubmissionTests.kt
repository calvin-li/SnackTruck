package com.example.snacktruck


import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test

import org.junit.Before

@LargeTest
class OrderSubmissionTests {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    private val submitButton = onView(
        allOf(
            withId(R.id.submitButton), withText("Submit Order"),
            isDisplayed()
        )
    )
    private lateinit var orderedItem: ViewInteraction

    @Before
    fun setup() {
        orderedItem = onView(
            allOf(
                withId(R.id.order_for_confirmation),
                withText("French fries")
            )
        )
    }

    @Test
    fun zeroOrdersTest() {
        submitButton.perform(click())
        orderTest(emptyList())
    }

    @Test
    fun oneOrderTest(){
        val itemsToTest = listOf("French fries")
        orderTest(itemsToTest)
    }

    @Test
    fun twoOrdersTest(){
        val itemsToTest = listOf("French fries", "Apple")
        orderTest(itemsToTest)
    }

    @Test
    fun twoOrdersDifferentCategoriesTest(){
        val itemsToTest = listOf("French fries", "Hot dog")
        orderTest(itemsToTest)
    }

    @Test
    fun orderConfirmTest(){
        val itemsToTest = listOf("French fries")
        orderTest(itemsToTest)
        onView(
            allOf(withText("Confirm"))
        ).perform(click())
        orderTest(emptyList())
    }

    @Test
    fun orderCancelTest(){
        val itemsToTest = listOf("French fries")
        orderTest(itemsToTest)
        onView(
            allOf(withText("Cancel"))
        ).perform(click())
        submitButton.perform((click()))
        checkOrderedItems(itemsToTest)
    }

    private fun orderTest(itemsToTest: List<String>) {
        itemsToTest.map { selectMenuItem(it) }
        submitButton.perform((click()))
        checkOrderedItems(itemsToTest)
    }

    private fun checkOrderedItems(itemsToTest: List<String>) {
        // Check that every ordered item is displayed
        itemsToTest.map {
            onView(
                allOf(
                    withId(R.id.order_for_confirmation),
                    withText(it)
                )
            ).check(matches(isDisplayed()))
        }

        // Check that no other items are displayed
        if (itemsToTest.count() > 0) {
            onView(
                allOf(
                    withId(R.id.order_list),
                    hasChildCount(itemsToTest.count())
                )
            ).check(matches(isDisplayed()))
        } else{
            onView(withId(R.id.order_list)).check(doesNotExist())
        }
    }

    private fun selectMenuItem(itemName: String) {
        val snackContainerMatcher = allOf(
            withId(R.id.snack_item_container),
            withChild(
                withText(itemName)
            ),
            withParent(
                withId(R.id.snack_list)
            )
        )

        onView(
            allOf(
                withParent(snackContainerMatcher),
                withId(R.id.snack_checkbox),
                isDisplayed()
            )
        ).perform(click())
    }

}
