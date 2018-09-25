package com.example.android.quakereport

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.example.android.quakereport.base.BaseTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso
import com.example.android.quakereport.pageObjects.EarthquakeListPage
import com.example.android.quakereport.pageObjects.OrderTypes
import com.example.android.quakereport.pageObjects.QuakeReportSettingsPage
import okreplay.OkReplay
import org.junit.Before
import org.junit.After

@RunWith(AndroidJUnit4::class)
class EarthquakeTest: BaseTest() {

    @Before
    fun registerIdlingResource() {
        Espresso.registerIdlingResources(
                intentsRule.getActivity().getCountingIdlingResource())
    }

    @OkReplay
    @Test
    fun earthquakeActivityTest() {
        EarthquakeListPage().goToQuakeReportForPlace("Suva, Fiji").magnitudeTitle.check(matches(withText("EARTHQUAKE STRENGTH")))

       takeFalconSpoonScreenshot("screen 1")
    }

    @Test
    fun changeSettingsForOrder() {
        EarthquakeListPage().goToQuakeReportSettings()
        takeFalconSpoonScreenshot("before change")
        QuakeReportSettingsPage().setOrderByTo(OrderTypes.RECENT)
        takeFalconSpoonScreenshot("orderType")
    }

    // Todo: intentstest for visit website button

    @After
    fun unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                intentsRule.getActivity().getCountingIdlingResource())
    }

}
