package com.example.android.quakereport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class QuakeAdapter(context: Context, listOfEarthQuakes: ArrayList<EarthQuake>) : ArrayAdapter<EarthQuake>(context, 0, listOfEarthQuakes) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            // Check if the existing view is being reused, otherwise inflate the view
            var listItemView = convertView
            if (listItemView == null) {
                listItemView = LayoutInflater.from(context).inflate(
                        R.layout.eartquake_list_item, parent, false)
            }

            //Get the correct color that the resource ID represents and set it as background, both methods stated below work, but maybe not all android versions?
            //listItemView?.findViewById(R.id.list_item_view)?.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
           // listItemView?.findViewById<View>(R.id.list_item_view)?.setBackgroundResource(backgroundColor)

            // Get the {@link AndroidFlavor} object located at this position in the list
            val currentEartquake = getItem(position)

            // Find the TextView in the list_item.xml layout with the ID version_name
            val eartQuakeMagnitude = listItemView!!.findViewById(R.id.earthquake_magnitude) as TextView
            // Get the version name from the current AndroidFlavor object and
            // set this text on the name TextView
            eartQuakeMagnitude.setText(currentEartquake.magnitude.toString())

            // Find the TextView in the list_item.xml layout with the ID version_number
            val earthQuakePlace = listItemView.findViewById(R.id.earthquake_place) as TextView
            // Get the version number from the current AndroidFlavor object and
            // set this text on the number TextView
            earthQuakePlace.setText(currentEartquake.place)


            // Find the TextView in the list_item.xml layout with the ID version_name
            val earthQuakeDate = listItemView.findViewById(R.id.earthquake_date) as TextView
            // Get the version name from the current AndroidFlavor object and
            // set this text on the name TextView
            earthQuakeDate.setText(currentEartquake.date)


//            val icon = listItemView.findViewById(R.id.list_item_icon) as ImageView
//            if (currentWord.imageResourceId != null) {
//                icon.setImageResource(currentWord.imageResourceId)
//                icon.setVisibility(View.VISIBLE)
//            } else {
//                icon.setVisibility(View.GONE)
//            }
            // Return the whole list item layout (containing 2 TextViews and an ImageView)
            // so that it can be shown in the ListView
            return listItemView
        }
}