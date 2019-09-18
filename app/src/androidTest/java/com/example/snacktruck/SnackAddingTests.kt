package com.example.snacktruck

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule

import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers
import org.hamcrest.beans.HasPropertyWithValue.hasProperty

import org.junit.Rule
import org.junit.Test

@LargeTest
class SnackAddingTests {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)
}