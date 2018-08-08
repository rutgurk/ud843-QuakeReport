package com.example.android.quakereport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.android.quakereport.FormatUtils.formatDate
import com.example.android.quakereport.FormatUtils.formateTime

class QuakeAdapter(context: Context, listOfEarthQuakes: ArrayList<EarthQuake>) : ArrayAdapter<EarthQuake>(context, 0, listOfEarthQuakes) {
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
            eartQuakeMagnitude.setText(currentEarthquake.magnitude.toString())

            // Find the TextView in the list_item.xml layout with the ID version_number
            val earthQuakePlace = listItemView.findViewById(R.id.earthquake_place) as TextView
            // Get the version number from the current AndroidFlavor object and
            // set this text on the number TextView
            earthQuakePlace.setText(currentEarthquake.place)
            
            // Find the TextView in the list_item.xml layout with the ID version_name
            val earthQuakeDate = listItemView.findViewById(R.id.earthquake_date) as TextView
            // Get the version name from the current AndroidFlavor object and
            // set this text on the name TextView
            val earthQuakeDateTime = formatDate(currentEarthquake.date) + "\n" + formateTime(currentEarthquake.date)
            earthQuakeDate.setText(earthQuakeDateTime)

            return listItemView
        }
}