package com.example.android.quakereport.base

import android.content.Context
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.view.View
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

object TestUtils {
    private val KEY_SP_PACKAGE = "com.example.android.quakereport_preferences"
    private val MAX_FIND_VIEWINTERACTION_TRIES = 70
    private val TIME_TO_WAIT_BETWEEN_TRIES = 500

    /**
     * Clears everything in the SharedPreferences
     */
    fun clearSharedPrefs(context: Context) {
        val prefs = context.getSharedPreferences(KEY_SP_PACKAGE, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

    fun waitForLoadingOfView(viewMatcher: Matcher<View>): Boolean {
        var findViewInteractionCounter = 0
        while (findViewInteractionCounter < MAX_FIND_VIEWINTERACTION_TRIES) {
            if (viewExists(viewMatcher)) {
                return true
            } else {
                findViewInteractionCounter += 1
                wait(TIME_TO_WAIT_BETWEEN_TRIES)
            }
        }
        throw NoMatchingViewAfterWaitException("View not found after waiting ${viewMatcher}")
    }


    fun viewExists(viewMatcher: Matcher<View>): Boolean {
        try {
            onView(allOf(viewMatcher, isDisplayed())).check(matches(isDisplayed()))
            return true
        } catch (e: NoMatchingViewException) {
            try {
                // View was not completely displayed, so try to scroll to it.
                onView(viewMatcher).perform(scrollTo())
            } catch (e: Exception) {
                // Ignore this one.
            }
            return false
        }
    }

    fun wait(milliseconds: Int) {
        try {
            Thread.sleep(milliseconds.toLong())
        } catch (e: InterruptedException) {

        }
    }
}

class NoMatchingViewAfterWaitException(message: String) : Exception(message)