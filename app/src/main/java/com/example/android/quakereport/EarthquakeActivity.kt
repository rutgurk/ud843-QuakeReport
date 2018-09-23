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

import android.app.LoaderManager
import android.content.Intent
import android.content.Loader
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast

class EarthquakeActivity : LoaderManager.LoaderCallbacks<List<EarthQuake>>, AppCompatActivity() {

    private val USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10"
    private val EARTHQUAKE_LOADER_ID = 1

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<EarthQuake>> {
        Log.i(LOG_TAG, "Loader info: onCreateLoader has been called")
        return EarthQuakeLoader(this, USGS_URL)
    }

    override fun onLoadFinished(loader: Loader<List<EarthQuake>>, data: List<EarthQuake>?) {
        Log.i(LOG_TAG, "Loader info: onLoadFinished has been called")
        adapter.clear()
        // If there is no result, do nothing.
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data)
        }
    }

    override fun onLoaderReset(loader: Loader<List<EarthQuake>>) {
        Log.i(LOG_TAG, "Loader info: onLoaderReset has been called")
        //adapter.addAll(ArrayList<EarthQuake>())
        adapter.clear()
    }

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
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this) //.forceLoad()
        Log.i(LOG_TAG, "Loader info: Loader has been initialized")
    }

    companion object {

        val LOG_TAG = EarthquakeActivity::class.simpleName
    }
}
