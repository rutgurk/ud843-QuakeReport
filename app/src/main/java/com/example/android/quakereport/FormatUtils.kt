package com.example.android.quakereport

import android.content.Context
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object FormatUtils {

    private val LOCATION_SEPARATOR = " of "
    private val MAGNITUDE_FORMAT = "0.0"
    private val TIME_FORMAT = "h:mm a"
    private val DATE_FORMAT = "MMM DD, yyyy"

    fun formatDate(timeInMiliseconds: Long): String {
        return SimpleDateFormat(DATE_FORMAT).format(Date(timeInMiliseconds))
    }

    fun formatTime(timeInMiliseconds: Long): String {
        return SimpleDateFormat(TIME_FORMAT).format(Date(timeInMiliseconds))
    }

    fun formatMagnitude(magnitude: Double): String {
        return DecimalFormat(MAGNITUDE_FORMAT).format(magnitude)
    }
    fun extractEarthQuakeProximity(context: Context, place: String): String {
        if (place.contains(LOCATION_SEPARATOR)) {
           val proximityAndPlace = place.split(LOCATION_SEPARATOR)
            return proximityAndPlace[0] + " of"
        } else {
            return context.getString(R.string.proximity_copy)
        }
    }

    fun extractEarthQuakePlace(place: String): String {
        if (place.contains(LOCATION_SEPARATOR)) {
            val proximityAndPlace = place.split(LOCATION_SEPARATOR)
            return proximityAndPlace[1]
        } else {
            return place
        }
    }
}
