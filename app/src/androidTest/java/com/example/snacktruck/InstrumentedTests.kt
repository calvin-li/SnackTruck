package com.example.snacktruck

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

/**
 * UI Tests:
 *  Confirm select state is correct (both visually and in data) for the following operations:
 *      - Confirming order
 *      - Canceling order
 *      - Leaving app
 *      - Filtering
 *      - Adding new snacks
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTests {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.snacktruck", appContext.packageName)
    }
}
