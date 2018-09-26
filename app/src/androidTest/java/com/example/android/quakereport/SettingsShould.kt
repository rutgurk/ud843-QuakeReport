package com.example.android.quakereport


import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.example.android.quakereport.base.BaseTest
import com.example.android.quakereport.pageObjects.EarthquakeListPage
import com.example.android.quakereport.pageObjects.OrderTypes
import com.example.android.quakereport.pageObjects.QuakeReportSettingsPage
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SettingsShould: BaseTest() {

    @Test
    fun displayChangedMagnitudeValue() {
        EarthquakeListPage()
                .goToQuakeReportSettings()
                .magnitudePreferenceValue.check(ViewAssertions.matches(withText("2")))
        takeFalconSpoonScreenshot("displayChangedMagnitudeValue_beforeCange")

        QuakeReportSettingsPage()
                .setOrderByTo(OrderTypes.RECENT)
                .setMagnitudeTo(7)
                .magnitudePreferenceValue.check(ViewAssertions.matches(withText("7")))
        takeFalconSpoonScreenshot("displayChangedMagnitudeValue_afterChange")
    }
}
