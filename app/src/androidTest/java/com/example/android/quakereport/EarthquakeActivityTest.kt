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
import okreplay.OkReplay
import org.junit.Before
import org.junit.After





@RunWith(AndroidJUnit4::class)
class EarthquakeActivityTest: BaseTest() {

    @Before
    fun registerIdlingResource() {
        Espresso.registerIdlingResources(
                intentsRule.getActivity().getCountingIdlingResource())
    }

    @OkReplay
    @Test
    fun earthquakeActivityTest() {
        val textView = onView(
                allOf(withId(R.id.earthquake_magnitude), withText("7.9"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list),
                                        0),
                                0),
                        isDisplayed()))
        textView.check(matches(withText("7.9")))

        Thread.sleep(5000)

        val linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.list),
                        childAtPosition(
                                withClassName(`is`("android.widget.RelativeLayout")),
                                0)))
                .atPosition(0)
        linearLayout.perform(click())

        val textView2 = onView(
                allOf(withText("EARTHQUAKE STRENGTH"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()))
        textView2.check(matches(withText("EARTHQUAKE STRENGTH")))

        val button = onView(
                allOf(withId(R.id.visit_website_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()))
        button.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    @After
    fun unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(
                intentsRule.getActivity().getCountingIdlingResource())
    }

}
