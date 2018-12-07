package com.example.android.quakereport.pageObjects

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.*
import com.example.android.quakereport.R
import com.example.android.quakereport.base.Page
import com.example.android.quakereport.base.TestUtils.waitForLoadingOfView
import org.hamcrest.CoreMatchers.allOf
import com.example.android.quakereport.base.EarthQuakePlaceNameMatcher.Companion.withEarthquakePlaceName


class EarthquakeListPage: Page() {
    override fun waitForLoading() {
        waitForLoadingOfView(allOf(withId(R.id.action_bar), withChild(withText(R.string.app_name))))
    }

    private var quakeReportSettingsButton = onView(withId(R.id.action_settings))

    fun goToQuakeReportSettings(): QuakeReportSettingsPage {
        quakeReportSettingsButton.perform(click())
        return QuakeReportSettingsPage()
    }

    fun goToEarthquakeReportForPlace(place: String): EarthquakeDetailPage {
        onData(withEarthquakePlaceName(place)).perform(click())
        //onView(allOf(earthquakePlaceMatcher, withText(place))).perform(click())
        return EarthquakeDetailPage()
    }

}



