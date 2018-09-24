package com.example.android.quakereport.base

import android.content.Context
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.allOf

object TestUtils {
    private val KEY_SP_PACKAGE = "com.example.android.quakereport.preferences"

    /**
     * Clears everything in the SharedPreferences
     */
    fun clearSharedPrefs(context: Context) {
        val prefs = context.getSharedPreferences(KEY_SP_PACKAGE, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

    fun viewExists(resourceId: Int): Boolean {
        try {
            onView(allOf(withId(resourceId), isDisplayed())).check(matches(isDisplayed()))
            return true
        } catch (e: NoMatchingViewException) {
            try {
                // View was not completely displayed, so try to scroll to it.
                onView(withId(resourceId)).perform(scrollTo())
            } catch (e: Exception) {
                // Ignore this one.
            }
            return false
        }
    }
}