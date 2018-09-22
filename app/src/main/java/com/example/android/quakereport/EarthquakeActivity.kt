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
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.widget.Toast

class EarthquakeActivity : AppCompatActivity() {

    lateinit var adapter: QuakeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_activity)
        // Find a reference to the {@link ListView} in the layout
        val earthquakeListView = findViewById<View>(R.id.list) as ListView

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = QuakeAdapter(
                this, ArrayList<EarthQuake>())
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.adapter = adapter
        // Set a click listener on that View
        earthquakeListView.setOnItemClickListener{ _, _, position, _ ->
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adapter.getItem(position).url))
                this.startActivity(intent)
            } catch (e: Throwable) {
                Toast.makeText(this, "Het openen van de link is niet gelukt", Toast.LENGTH_LONG)
                        .apply {
                            show() }
            }
        }
        ExtractEarthQuakes().execute("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10")
    }

    private inner class ExtractEarthQuakes: AsyncTask<String, Void, List<EarthQuake>>() {
        override fun doInBackground(vararg urls: String): List<EarthQuake>? {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.size < 1 || urls[0] == null) {
                return null
            }
            return QueryUtils.fetchEarthquakeData(urls[0])
        }

        override fun onPostExecute(results: List<EarthQuake>?) {
            adapter.clear()
            // If there is no result, do nothing.
            if (results != null && !results.isEmpty()) {
                adapter.addAll(results)
            }
        }

    }

    companion object {

        val LOG_TAG = EarthquakeActivity::class.simpleName
    }
}
