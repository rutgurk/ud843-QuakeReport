package com.example.android.quakereport.base

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.rule.IntentsTestRule
import com.example.android.quakereport.EarthquakeActivity
import org.junit.Rule

abstract class BaseTest {

    private var currentActivity: Activity? = null

    @get:Rule
    var activityTestRule: IntentsTestRule<EarthquakeActivity> = object : IntentsTestRule<EarthquakeActivity>(EarthquakeActivity::class.java) {
        override fun beforeActivityLaunched() {
            TestUtils.clearSharedPrefs(InstrumentationRegistry.getTargetContext())
            super.beforeActivityLaunched()
        }
    }
}