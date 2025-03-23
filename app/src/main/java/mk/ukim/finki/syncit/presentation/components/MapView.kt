package mk.ukim.finki.syncit.presentation.components

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import mk.ukim.finki.syncit.domain.services.GeocodingService
import org.osmdroid.util.GeoPoint

@SuppressLint("ClickableViewAccessibility")
@Composable
fun MapView(
    location: Pair<Double, Double>?,
    onLocationSelected: (Pair<Double, Double>) -> Unit
) {
    val context = LocalContext.current

    // Initialize MapView directly inside AndroidView
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val mapView = MapView(context)
            val prefs = context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
            Configuration.getInstance().load(context, prefs)
            mapView.controller.setZoom(15.0)
            mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
            mapView.setMultiTouchControls(true)

            // Set the marker if location is passed
            location?.let {
                val geoPoint = GeoPoint(it.first, it.second)
                mapView.controller.setCenter(geoPoint)

                val marker = Marker(mapView)
                marker.position = geoPoint
                marker.title = "Selected Location"
                mapView.overlays.add(marker)
            }

            // Handle map click and update location using onTouchListener
            mapView.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    // Calculate the clicked geo point
                    val geoPoint = mapView.projection.fromPixels(event.x.toInt(), event.y.toInt())
                    onLocationSelected(Pair(geoPoint.latitude, geoPoint.longitude))
                }
                true
            }

            mapView
        }
    )
}