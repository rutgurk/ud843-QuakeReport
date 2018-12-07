package com.example.android.quakereport

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.example.android.quakereport.base.BaseTest
import com.example.android.quakereport.pageObjects.EarthquakeListPage
import com.example.android.quakereport.pageObjects.OrderTypes
import com.example.android.quakereport.pageObjects.QuakeReportSettingsPage
import okreplay.OkReplay
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SettingsShould: BaseTest() {

    @Before
    fun registerIdlingResource() {
        Espresso.registerIdlingResources(
                intentsRule.getActivity().getCountingIdlingResource())
    }

    @OkReplay
    @Test
    fun displayChangedMagnitudePreferenceValue() {
        EarthquakeListPage()
                .goToQuakeReportSettings()
                .magnitudePreferenceValue.check(matches(withText("2")))
        takeFalconSpoonScreenshot("displayChangedMagnitudeValue_beforeChange")

        QuakeReportSettingsPage()
                .setMagnitudeTo(7)
                .magnitudePreferenceValue.check(matches(withText("7")))
        takeFalconSpoonScreenshot("displayChangedMagnitudeValue_afterChange")
    }

    @OkReplay
    @Test
    fun displayChangedOrderByPreferenceValue() {
        EarthquakeListPage()
                .goToQuakeReportSettings()
                .orderByPreferenceValue.check(matches(withText(OrderTypes.MAGNITUDE.typeText)))
        takeFalconSpoonScreenshot("displayChangedOrderByPreferenceValue_beforeChange")

        QuakeReportSettingsPage()
                .setOrderByTo(OrderTypes.RECENT)
                .orderByPreferenceValue.check(matches(withText(OrderTypes.RECENT.typeText)))
        takeFalconSpoonScreenshot("displayChangedOrderByPreferenceValue_afterChange")
    }

    @OkReplay
    @Test
    fun displayChangedAmountOfSearchResultsPreferenceValue() {
        EarthquakeListPage()
                .goToQuakeReportSettings()
                .amountOfSearchResultsValue.check(matches(withText("10")))
        takeFalconSpoonScreenshot("displayChangedAmountOfSearchResultsPreferenceValue_beforeChange")

        QuakeReportSettingsPage()
                .setMaxAmountOfResultsTo(2)
                .amountOfSearchResultsValue.check(matches(withText("2")))
        takeFalconSpoonScreenshot("displayChangedAmountOfSearchResultsPreferenceValue_afterChange")

        pressBack()
    }

    @After
    fun unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                intentsRule.getActivity().getCountingIdlingResource())
    }

}
