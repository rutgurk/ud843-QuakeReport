package com.example.android.quakereport.pageObjects

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import com.example.android.quakereport.R
import com.example.android.quakereport.base.Page
import com.example.android.quakereport.base.TestUtils
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf

class EarthquakeDetailPage: Page() {
    override fun waitForLoading() {
        TestUtils.waitForLoadingOfView(allOf(withId(R.id.action_bar), withChild(withText(R.string.earthquake_detail_name))))
    }

    var visitWebsiteButton = onView(withId(R.id.visit_website_button))
    var magnitudeTitle = onView(withId(R.id.magnitude_title))
}