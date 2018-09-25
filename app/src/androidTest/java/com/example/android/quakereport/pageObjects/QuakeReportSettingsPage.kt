package com.example.android.quakereport.pageObjects

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.PreferenceMatchers.withTitleText
import android.support.test.espresso.matcher.ViewMatchers.*
import com.example.android.quakereport.R
import com.example.android.quakereport.base.Page
import com.example.android.quakereport.base.TestUtils
import org.hamcrest.CoreMatchers.*

class QuakeReportSettingsPage: Page()  {
    override fun waitForLoading() {
        TestUtils.waitForLoadingOfView(allOf(withId(R.id.action_bar), withChild(withText(R.string.settings_title))))
    }

    private var orderBy = onData(withTitleText("Order By"))

    fun setOrderByTo(type: OrderTypes):QuakeReportSettingsPage {
        orderBy.perform(click())
        onData(containsString(type.typeText)).perform(click())
        return this
    }
}

enum class OrderTypes(val typeText: String) {
    MAGNITUDE("Magnitude"),
    RECENT("Most Recent")
}
