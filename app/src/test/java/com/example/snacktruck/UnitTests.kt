package com.example.snacktruck

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

// TODO: Choose a better name
class UnitTests {
    private val testSnacksJsonRaw = "{\n" +
            "  \"Veggie\" : [\n" +
            "    \"French fries\",\n" +
            "    \"Milkshake\"\n" +
            "  ],\n" +
            "  \"Non-Veggie\" : [\n" +
            "    \"Hot dog\"\n" +
            "  ]\n" +
            "}"
    private val testSnacksJson = Parser.default().parse(StringBuilder(testSnacksJsonRaw)) as JsonObject

    @Test
    fun getSnackList_noNewSnacks() {
        val testActivity = MainActivity()
        testActivity.snacksJson = testSnacksJson
        val testSnackList = testActivity.getSnackList()

        val expectedSnackList = listOf(
            Snack("Veggie", "French fries", false),
            Snack("Veggie", "Milkshake", false),
            Snack("Non-Veggie", "Hot dog", false)
        )
        assertEquals(expectedSnackList, testSnackList)
    }

    @Test
    fun getSnackList_newSnacks() {
        val testActivity = MainActivity()
        testActivity.snacksJson = testSnacksJson
        testActivity.newSnacks = listOf(
            Snack("Veggie", "Veggie dog", false)
        )
        val testSnackList = testActivity.getSnackList()

        val expectedSnackList = listOf(
            Snack("Veggie", "French fries", false),
            Snack("Veggie", "Milkshake", false),
            Snack("Non-Veggie", "Hot dog", false),
            Snack("Veggie", "Veggie dog", false)
        )

        assertEquals(expectedSnackList, testSnackList)
    }

    @Test
    fun snack_onClick(){
        val testActivity = MainActivity()
        val testSnackList = listOf(
            Snack("cat1", "snack1"),
            Snack("cat1", "snack2"),
            Snack("cat2", "snack3")
        )

        testActivity.snackMenu = testSnackList
        testActivity.toggleSnack("snack1")
        val expectedSnackList = listOf(
            Snack("cat1", "snack1", true),
            Snack("cat1", "snack2"),
            Snack("cat2", "snack3")
        )
        assertEquals(expectedSnackList, testActivity.snackMenu)

        testActivity.toggleSnack("snack2")
        expectedSnackList[1].selected = true
        assertEquals(expectedSnackList, testActivity.snackMenu)

        testActivity.toggleSnack("snack1")
        expectedSnackList[0].selected = false
        assertEquals(expectedSnackList, testActivity.snackMenu)

        testActivity.toggleSnack("snack2")
        expectedSnackList[1].selected = false
        assertEquals(expectedSnackList, testActivity.snackMenu)
    }
}
