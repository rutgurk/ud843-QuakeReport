package com.example.android.quakereport

import android.content.Intent
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import com.example.android.quakereport.base.BaseTest
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.matcher.UriMatchers
import com.example.android.quakereport.pageObjects.EarthquakeListPage
import okreplay.OkReplay
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.After

@RunWith(AndroidJUnit4::class)
class EarthquakeDetailShould: BaseTest() {

    @Before
    fun registerIdlingResource() {
        Espresso.registerIdlingResources(
                intentsRule.getActivity().getCountingIdlingResource())
    }

    @OkReplay
    @Test
    fun haveTitleForMagnitude() {
        EarthquakeListPage()
                .goToEarthquakeReportForPlace("209km W of Ile Hunter, New Caledonia")
                .magnitudeTitle.check(matches(withText("EARTHQUAKE STRENGTH")))

       takeFalconSpoonScreenshot("haveTitleForMagnitude")
    }

    @Test
    fun linkToUSGSWebsite() {
        EarthquakeListPage().goToEarthquakeReportForPlace("Suva, Fiji").visitWebsiteButton.perform(click())

        Intents.intended(CoreMatchers.allOf(
                IntentMatchers.hasAction(CoreMatchers.equalTo(Intent.ACTION_VIEW)),
                IntentMatchers.hasData(UriMatchers.hasHost(CoreMatchers.equalTo("earthquake.usgs.gov")))))
    }

    @After
    fun unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                intentsRule.getActivity().getCountingIdlingResource())
    }

}
