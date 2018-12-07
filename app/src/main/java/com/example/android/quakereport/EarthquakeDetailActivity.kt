package com.example.android.quakereport

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.android.quakereport.FormatUtils.formatDate
import com.example.android.quakereport.FormatUtils.formatTime


class EarthquakeDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_detail_activity)

        val earthquake = intent.getParcelableExtra<Earthquake>("earthquake")

        val magnitudeTextView = findViewById<View>(R.id.perceived_magnitude) as TextView
        magnitudeTextView.setText(earthquake.magnitude.toString())

        val titleTextView = findViewById<View>(R.id.title) as TextView
        titleTextView.setText(earthquake.place)

        val tsunamiTextView = findViewById<View>(R.id.number_of_people) as TextView
        tsunamiTextView.text = formatDate(earthquake.date) + " - " + formatTime(earthquake.date)

        val visitWebsiteButton = findViewById<View>(R.id.visit_website_button) as Button
        visitWebsiteButton.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(earthquake.url))
                this.startActivity(intent)
            } catch (e: Throwable) {
                Toast.makeText(this, "Het openen van de link is niet gelukt", Toast.LENGTH_LONG)
                        .apply {
                            show()
                        }
            }
        }
    }
}