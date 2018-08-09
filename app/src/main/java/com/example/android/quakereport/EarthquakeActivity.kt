/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.widget.Toast

class EarthquakeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_activity)

        val earthquakes = QueryUtils.extractEarthquakes()

        // Find a reference to the {@link ListView} in the layout
        val earthquakeListView = findViewById<View>(R.id.list) as ListView

        // Create a new {@link ArrayAdapter} of earthquakes
        val adapter = QuakeAdapter(
                this, earthquakes)
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.adapter = adapter
        // Set a click listener on that View
        earthquakeListView.setOnItemClickListener{ _, _, position, _ ->
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(earthquakes[position].url))
                    this.startActivity(intent)
                } catch (e: Throwable) {
                    Toast.makeText(this, "Het openen van de link is niet gelukt", Toast.LENGTH_LONG)
                            .apply {
                                show()
                            }
                }
            }
    }

    companion object {

        val LOG_TAG = EarthquakeActivity::class.simpleName
    }
}
