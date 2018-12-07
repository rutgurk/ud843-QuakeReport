package com.example.android.quakereport


import android.util.Log
import com.example.android.quakereport.EarthquakeActivity.Companion.LOG_TAG
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset
import okhttp3.OkHttpClient
import okhttp3.Request
import okreplay.OkReplayInterceptor


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
object QueryUtils {

    val okReplayInterceptor = OkReplayInterceptor()
    /**
     * Query the USGS dataset and return a list of earthQuakes.
     */
    fun fetchEarthquakeData(requestUrl: String): List<Earthquake>? {
        // Create URL object
        val url = createUrl(requestUrl)

        // Perform HTTP request to the URL and receive a JSON response back
        var jsonResponse: String? = null
        try {
            jsonResponse = makeHttpRequest(url)
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Error closing input stream", e)
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object

        // Return the {@link Event}
        if (jsonResponse == null) {
            return null
        } else {
        return extractEarthquakes(jsonResponse)
        }
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private fun createUrl(stringUrl: String): URL? {
        var url: URL? = null
        try {
            url = URL(stringUrl)
        } catch (e: MalformedURLException) {
            Log.e(LOG_TAG, "Error with creating URL ", e)
        }

        return url
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    @Throws(IOException::class)
    private fun makeHttpRequest(url: URL?): String {
        val client = OkHttpClient.Builder().addInterceptor(okReplayInterceptor).build()
//        var jsonResponse = ""
//
//        // If the URL is null, then return early.
//        if (url == null) {
//            return jsonResponse
//        }
//
//        var urlConnection: HttpURLConnection? = null
//        var inputStream: InputStream? = null
//        try {
//            urlConnection = url!!.openConnection() as HttpURLConnection
//            urlConnection!!.setReadTimeout(10000 /* milliseconds */)
//            urlConnection!!.setConnectTimeout(15000 /* milliseconds */)
//            urlConnection!!.setRequestMethod("GET")
//            urlConnection!!.connect()
//
//            // If the request was successful (response code 200),
//            // then read the input stream and parse the response.
//            if (urlConnection!!.getResponseCode() === 200) {
//                inputStream = urlConnection!!.getInputStream()
//                jsonResponse = readFromStream(inputStream)
//            } else {
//                Log.e(LOG_TAG, "Error response code: " + urlConnection!!.getResponseCode())
//            }
//        } catch (e: IOException) {
//            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e)
//        } finally {
//            if (urlConnection != null) {
//                urlConnection!!.disconnect()
//            }
//            if (inputStream != null) {
//                inputStream!!.close()
//            }
//        }

        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request).execute()
        return response.body()!!.string()
    }

    /**
     * Convert the [InputStream] into a String which contains the
     * whole JSON response from the server.
     */
    @Throws(IOException::class)
    private fun readFromStream(inputStream: InputStream?): String {
        val output = StringBuilder()
        if (inputStream != null) {
            val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
            val reader = BufferedReader(inputStreamReader)
            var line = reader.readLine()
            while (line != null) {
                output.append(line)
                line = reader.readLine()
            }
        }
        return output.toString()
    }


    /**
     * Return a list of [Earthquake] objects that has been built up from
     * parsing a JSON response.
     */
    private fun extractEarthquakes(jsonResponse: String): ArrayList<Earthquake> {

        // Create an empty ArrayList that we can start adding earthquakes to
        val earthquakes = ArrayList<Earthquake>()

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            val earthQuakeFeatureCollection = JSONObject(jsonResponse)
            val earthQuakeFeatures = earthQuakeFeatureCollection.getJSONArray("features")
            var counter = 0
            while (counter <  earthQuakeFeatures.length()) {
                val earthQuakeProperties = earthQuakeFeatures.getJSONObject(counter).getJSONObject("properties")
                        earthquakes.add(Earthquake(earthQuakeProperties.getDouble("mag"), earthQuakeProperties.getString("place"), earthQuakeProperties.getLong("time"), earthQuakeProperties.getString("url")))
                counter ++
            }

        } catch (e: JSONException) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e)
            Log.e("QueryUtils", "Response: " + jsonResponse)
        }

        // Return the list of earthquakes
        return earthquakes
    }
}
