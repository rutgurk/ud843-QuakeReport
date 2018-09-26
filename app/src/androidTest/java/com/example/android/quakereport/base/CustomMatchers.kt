package com.example.android.quakereport.base

import android.support.test.espresso.matcher.BoundedMatcher
import com.example.android.quakereport.Earthquake
import org.hamcrest.Description

internal class EarthQuakePlaceNameMatcher private constructor(private val placeName: String) : BoundedMatcher<Any, Earthquake>(Earthquake::class.java) {
    override fun matchesSafely(item: Earthquake): Boolean {
        return item.place == placeName
    }

    override fun describeTo(description: Description) {
        description.appendText("with hint text placeName:")
                .appendValue(placeName)
    }

    companion object {
        fun withEarthquakePlaceName(placeName: String): EarthQuakePlaceNameMatcher {
            return EarthQuakePlaceNameMatcher(placeName)
        }
    }
}
