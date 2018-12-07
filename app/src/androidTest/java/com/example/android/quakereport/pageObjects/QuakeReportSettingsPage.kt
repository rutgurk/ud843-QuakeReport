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

class QuakeReportSettingsPage: Page()  {
    override fun waitForLoading() {
        TestUtils.waitForLoadingOfView(allOf(withId(R.id.action_bar), withChild(withText(R.string.settings_title))))
    }

//    private var orderByPreferenceTitle = onData(withTitleText("Order By"))
    private var magnitudePreferenceView = onData(withKey("min_magnitude_key"))
    private var orderByPreferenceView = onData(withKey("order_by_key"))
    private var amountOfSearchResultsView = onData(withKey("amount_results_key"))
    var orderByPreferenceValue = orderByPreferenceView.onChildView(allOf(withId(android.R.id.summary)))
    var magnitudePreferenceValue = magnitudePreferenceView.onChildView(allOf(withId(android.R.id.summary)))
    var amountOfSearchResultsValue = amountOfSearchResultsView.onChildView(allOf(withId(android.R.id.summary)))


    fun setOrderByTo(type: OrderTypes):QuakeReportSettingsPage {
        orderByPreferenceView.perform(click())
        //onView(allOf(withText("Order By"))).inRoot(isDialog()).check(matches(isDisplayed()))
        onData(containsString(type.typeText)).inRoot(isDialog()).perform(click())
        return this
    }

    fun setMagnitudeTo(magnitude: Int):QuakeReportSettingsPage {
        magnitudePreferenceView.perform(click())
        //onView(allOf(withText("Minimum Magnitude"))).inRoot(isDialog()).check(matches(isDisplayed()))
        onView(allOf(withId(android.R.id.edit))).inRoot(isDialog()).perform(ViewActions.scrollTo(), replaceText(magnitude.toString()))
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click())
        return this
    }

    fun setMaxAmountOfResultsTo(amountOfResults: Int):QuakeReportSettingsPage {
        amountOfSearchResultsView.perform(click())
        //onView(allOf(withText("Amount of Search Results"))).inRoot(isDialog()).check(matches(isDisplayed()))
        onView(allOf(withId(android.R.id.edit))).inRoot(isDialog()).perform(ViewActions.scrollTo(), replaceText(amountOfResults.toString()))
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click())
        return this
    }
}

enum class OrderTypes(val typeText: String) {
    MAGNITUDE("Magnitude"),
    RECENT("Most Recent")
}
