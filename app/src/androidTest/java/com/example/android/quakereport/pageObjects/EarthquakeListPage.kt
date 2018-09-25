package com.example.android.quakereport.pageObjects

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.*
import com.example.android.quakereport.R
import com.example.android.quakereport.base.Page
import com.example.android.quakereport.base.TestUtils.waitForLoadingOfView
import org.hamcrest.CoreMatchers.allOf

class EarthquakeListPage: Page() {
    override fun waitForLoading() {
        waitForLoadingOfView(allOf(withId(R.id.action_bar), withChild(withText(R.string.app_name))))
    }

    private var quakeReportSettingsButton = onView(withId(R.id.action_settings))

    private var earthquakeMagnitudeMatcher = withId(R.id.earthquake_magnitude)
    private var earthquakePlaceMatcher = withId(R.id.earthquake_place)
    private var earthquakeProximityMatcher = withId(R.id.earthquake_proximity)
    private var earthquakeDateMatcher = withId(R.id.earthquake_date)
    private var earthquakeTimeMatcher = withId(R.id.earthquake_time)

    fun goToQuakeReportSettings(): QuakeReportSettingsPage {
        quakeReportSettingsButton.perform(click())
        return QuakeReportSettingsPage()
    }

    fun goToQuakeReportForPlace(place: String): EarthquakeDetailPage {
        onView(allOf(earthquakePlaceMatcher, withText(place))).perform(click())
        return EarthquakeDetailPage()
    }

}