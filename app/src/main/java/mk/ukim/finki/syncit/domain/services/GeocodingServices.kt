package mk.ukim.finki.syncit.domain.services

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import org.osmdroid.util.GeoPoint
import java.net.HttpURLConnection
import java.net.URL

object GeocodingService {

    private val client = OkHttpClient()

    fun getCoordinatesFromAddress(address: String): GeoPoint? {
        val urlString = "https://nominatim.openstreetmap.org/search?q=$address&format=json"
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")


        try {
            val response = connection.inputStream.bufferedReader().use { it.readText() }

            val jsonArray = JSONArray(response)

            if (jsonArray.length() > 0) {
                val jsonObject = jsonArray.getJSONObject(0)
                val lat = jsonObject.getString("lat").toDoubleOrNull()
                val lon = jsonObject.getString("lon").toDoubleOrNull()

                if (lat != null && lon != null) {
                    return GeoPoint(lat, lon)
                }
            }
        }
        catch (e: Exception) {
            Log.e("GeocodingService", "Geocoding failed", e)
        }

        return null
    }

    fun getAddressFromCoordinates(lat: Double, lon: Double): String? {
        val url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=$lat&lon=$lon"
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "SyncItApp/1.0")
            .build()

        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val jsonResponse = JSONObject(response.body?.string() ?: "{}")
                return jsonResponse.getString("display_name")
            }
        }
        return null
    }
}