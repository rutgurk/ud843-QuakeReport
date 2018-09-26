package com.example.android.quakereport.pageObjects

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.PreferenceMatchers.*
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.ViewMatchers.*
import com.example.android.quakereport.R
import com.example.android.quakereport.base.Page
import com.example.android.quakereport.base.TestUtils
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers

class QuakeReportSettingsPage: Page()  {
    override fun waitForLoading() {
        TestUtils.waitForLoadingOfView(allOf(withId(R.id.action_bar), withChild(withText(R.string.settings_title))))
    }

//    private var orderByPreferenceTitle = onData(withTitleText("Order By"))
    private var magnitudePreferenceView = onData(withKey("min_magnitude_key"))
    private var orderByPreferenceView = onData(withKey("order_by_key"))
    var orderByPreferenceValue = orderByPreferenceView.onChildView(allOf(withId(android.R.id.summary)))
    var magnitudePreferenceValue = magnitudePreferenceView.onChildView(allOf(withId(android.R.id.summary)))


    fun setOrderByTo(type: OrderTypes):QuakeReportSettingsPage {
        //orderByPreferenceView.onChildView(allOf(withId(android.R.id.summary))).check(matches(withText("Magnitude")))
        orderByPreferenceView.perform(click())
        onView(allOf(withText("Order By"))).inRoot(isDialog()).check(matches(isDisplayed()))
        onData(containsString(type.typeText)).perform(click())
        return this
    }

    fun setMagnitudeTo(magnitude: Int):QuakeReportSettingsPage {
        magnitudePreferenceView.perform(click())
        onView(allOf(withText("Minimum Magnitude"))).inRoot(isDialog()).check(matches(isDisplayed()))
        onView(allOf(withId(android.R.id.edit))).inRoot(isDialog()).perform(ViewActions.scrollTo(), replaceText(magnitude.toString()))
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click())
        return this
    }

    //orderByPreferenceView.onChildView(allOf(withText("Magnitude"))).check(matches(withText("Magnitude")))
}

enum class OrderTypes(val typeText: String) {
    MAGNITUDE("Magnitude"),
    RECENT("Most Recent")
}
