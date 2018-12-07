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
import com.example.android.quakereport.base.EARTHQUAKEDETAIL_MAGNITUDE_TITLE
import com.example.android.quakereport.base.EARTHQUAKE_PLACENAME
import com.example.android.quakereport.base.USGS_BASE_URL
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
                .goToEarthquakeReportForPlace(EARTHQUAKE_PLACENAME)
                .magnitudeTitle.check(matches(withText(EARTHQUAKEDETAIL_MAGNITUDE_TITLE)))

       takeFalconSpoonScreenshot("haveTitleForMagnitude")
    }

    @OkReplay
    @Test
    fun linkToUSGSWebsite() {
        EarthquakeListPage()
                .goToEarthquakeReportForPlace(EARTHQUAKE_PLACENAME)
                .visitWebsiteButton.perform(click())

        Intents.intended(CoreMatchers.allOf(
                IntentMatchers.hasAction(CoreMatchers.equalTo(Intent.ACTION_VIEW)),
                IntentMatchers.hasData(UriMatchers.hasHost(CoreMatchers.equalTo(USGS_BASE_URL)))))
    }

    @After
    fun unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                intentsRule.getActivity().getCountingIdlingResource())
    }

}
