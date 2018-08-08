package com.example.android.quakereport

import java.text.SimpleDateFormat
import java.util.*

object FormatUtils {
    fun formatDate(timeInMiliseconds: Long): String {
        return SimpleDateFormat("MMM DD, yyyy").format(Date(timeInMiliseconds))
    }

    fun formateTime(timeInMiliseconds: Long): String {
        return SimpleDateFormat("h:mm a").format(Date(timeInMiliseconds))
    }
}