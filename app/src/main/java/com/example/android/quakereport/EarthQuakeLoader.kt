package com.example.android.quakereport

import android.content.AsyncTaskLoader
import android.content.Context
import android.util.Log
import com.example.android.quakereport.EarthquakeActivity.Companion.LOG_TAG

class EarthQuakeLoader(context: Context, val url: String): AsyncTaskLoader<List<EarthQuake>>(context) {
    override fun loadInBackground(): List<EarthQuake>? {
        Log.i(LOG_TAG, "Loader info: loadInBackground has been called")
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (url == null || url.isEmpty()) {
            return null
        }
        return QueryUtils.fetchEarthquakeData(url)
    }

    override fun onStartLoading() {
        Log.i(LOG_TAG, "Loader info: onStartLoading has been called")
        forceLoad()
    }
}