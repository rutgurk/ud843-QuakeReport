package com.example.android.quakereport

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat.getColor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.android.quakereport.FormatUtils.extractEarthQuakePlace
import com.example.android.quakereport.FormatUtils.extractEarthQuakeProximity
import com.example.android.quakereport.FormatUtils.formatDate
import com.example.android.quakereport.FormatUtils.formatMagnitude
import com.example.android.quakereport.FormatUtils.formatTime


class QuakeAdapter(context: Context, listOfEarthQuakes: List<EarthQuake>) : ArrayAdapter<EarthQuake>(context, 0, listOfEarthQuakes) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            // Check if the existing view is being reused, otherwise inflate the view
            var listItemView = convertView
            if (listItemView == null) {
                listItemView = LayoutInflater.from(context).inflate(
                        R.layout.eartquake_list_item, parent, false)
            }

            // Get the {@link AndroidFlavor} object located at this position in the list
            val currentEarthquake = getItem(position)

            // Find the TextView in the list_item.xml layout with the ID version_name
            val eartQuakeMagnitude = listItemView!!.findViewById(R.id.earthquake_magnitude) as TextView
            // Get the version name from the current AndroidFlavor object and
            // set this text on the name TextView
            eartQuakeMagnitude.setText(formatMagnitude(currentEarthquake.magnitude))

            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            val magnitudeCircle = eartQuakeMagnitude.getBackground() as GradientDrawable

            // Get the appropriate background color based on the current earthquake magnitude
            val magnitudeColor = getMagnitudeColor(currentEarthquake.magnitude)

            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor)

            // Find the TextView in the list_item.xml layout with the ID version_number
            val earthQuakeProximity = listItemView.findViewById(R.id.earthquake_proximity) as TextView
            val earthQuakePlace = listItemView.findViewById(R.id.earthquake_place) as TextView
            // Get the version number from the current AndroidFlavor object and
            // set this text on the number TextView
            earthQuakeProximity.setText(extractEarthQuakeProximity(context, currentEarthquake.place))
            earthQuakePlace.setText(extractEarthQuakePlace(currentEarthquake.place))

            // Find the TextView in the list_item.xml layout with the ID version_name
            val earthQuakeDate = listItemView.findViewById(R.id.earthquake_date) as TextView
            val earthQuakeTime = listItemView.findViewById(R.id.earthquake_time) as TextView
            // Get the version name from the current AndroidFlavor object and
            // set this text on the name TextView
            earthQuakeTime.setText(formatTime(currentEarthquake.date))
            earthQuakeDate.setText(formatDate(currentEarthquake.date))

            return listItemView
        }

    private fun getMagnitudeColor(magnitude: Double): Int {
        when(magnitude) {
            in 0.0..1.9 -> return getColor(context, R.color.magnitude1)
            in 2.0..2.9 -> return getColor(context, R.color.magnitude2)
            in 3.0..3.9 -> return getColor(context, R.color.magnitude3)
            in 4.0..4.9 -> return getColor(context, R.color.magnitude4)
            in 5.0..5.9 -> return getColor(context, R.color.magnitude5)
            in 6.0..6.9 -> return getColor(context, R.color.magnitude6)
            in 7.0..7.9 -> return getColor(context, R.color.magnitude7)
            in 8.0..8.9 -> return getColor(context, R.color.magnitude8)
            in 9.0..9.9 -> return getColor(context, R.color.magnitude9)
            else -> return getColor(context, R.color.magnitude10plus)
        }

    }
}