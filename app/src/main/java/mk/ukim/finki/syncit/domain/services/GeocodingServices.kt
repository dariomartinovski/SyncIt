package mk.ukim.finki.syncit.domain.services

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.osmdroid.util.GeoPoint

object GeocodingService {

    private val client = OkHttpClient()

    fun getCoordinatesFromAddress(address: String): GeoPoint? {
        val url = "https://nominatim.openstreetmap.org/search?format=json&q=$address"
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "SyncItApp/1.0")
            .build()

        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val jsonResponse = JSONObject(response.body?.string() ?: "{}")
                val lat = jsonResponse.getJSONArray("results").getJSONObject(0).getDouble("lat")
                val lon = jsonResponse.getJSONArray("results").getJSONObject(0).getDouble("lon")
                return GeoPoint(lat, lon)
            }
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