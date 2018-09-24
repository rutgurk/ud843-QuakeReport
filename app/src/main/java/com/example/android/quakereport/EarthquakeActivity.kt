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
import android.content.Context
import android.content.Intent
import android.content.Loader
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.preference.PreferenceManager


class EarthquakeActivity : LoaderManager.LoaderCallbacks<List<Earthquake>>, AppCompatActivity() {

    private val USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query"
    private val EARTHQUAKE_LOADER_ID = 1
    private lateinit var textViewForEmptyEarthQuakeListViewState: TextView
    lateinit var adapter: QuakeAdapter

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == R.id.action_settings) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Earthquake>> {
        Log.i(LOG_TAG, "Loader info: onCreateLoader has been called")
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default))

        val orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        )

        val baseUri = Uri.parse(USGS_URL)
        val uriBuilder = baseUri.buildUpon()

        uriBuilder.appendQueryParameter("format", "geojson")
        uriBuilder.appendQueryParameter("limit", "10")
        uriBuilder.appendQueryParameter("minmag", minMagnitude)
        uriBuilder.appendQueryParameter("orderby", orderBy)

        return EarthquakeLoader(this, uriBuilder.toString())
    }

    override fun onLoadFinished(loader: Loader<List<Earthquake>>, data: List<Earthquake>?) {
        Log.i(LOG_TAG, "Loader info: onLoadFinished has been called")
        val loadingIndicator = findViewById<View>(R.id.loading_indicator)
        loadingIndicator.visibility = View.GONE
        textViewForEmptyEarthQuakeListViewState.setText(R.string.no_earthquakes)
        adapter.clear()
        // If there is no result, do nothing.
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data)
        }
    }

    override fun onLoaderReset(loader: Loader<List<Earthquake>>) {
        Log.i(LOG_TAG, "Loader info: onLoaderReset has been called")
        //adapter.addAll(ArrayList<Earthquake>())
        adapter.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_activity)


        // Find a reference to the {@link ListView} in the layout
        val earthquakeListView = findViewById<View>(R.id.list) as ListView
        textViewForEmptyEarthQuakeListViewState = findViewById(R.id.empty_view)
        earthquakeListView.setEmptyView(textViewForEmptyEarthQuakeListViewState)

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = QuakeAdapter(
                this, ArrayList<Earthquake>())
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.adapter = adapter
        // Set a click listener on that View
        earthquakeListView.setOnItemClickListener { _, _, position, _ ->
            //            try {
            val intent = Intent(this, EarthquakeDetailActivity::class.java)
            intent.putExtra("earthquake", adapter.getItem(position))
            startActivity(intent)
        }
//
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adapter.getItem(position).url))
//                this.startActivity(intent)
//            } catch (e: Throwable) {
//                Toast.makeText(this, "Het openen van de link is niet gelukt", Toast.LENGTH_LONG)
//                        .apply {
//                            show() }
//            }


        var connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this) //.forceLoad()
            Log.i(LOG_TAG, "Loader info: Loader has been initialized")
        } else {
            //Should this be moved to onloadfinished?
            val loadingIndicator = findViewById<View>(R.id.loading_indicator)
            loadingIndicator.visibility = View.GONE
            textViewForEmptyEarthQuakeListViewState.setText(R.string.no_internet_connection)
        }
    }

    companion object {
        val LOG_TAG = EarthquakeActivity::class.simpleName
    }
}
