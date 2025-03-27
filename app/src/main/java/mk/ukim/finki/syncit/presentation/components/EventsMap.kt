package mk.ukim.finki.syncit.presentation.components

import CustomInfoWindow
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import mk.ukim.finki.syncit.R
import mk.ukim.finki.syncit.data.model.Event
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun EventsMap(events: List<Event>, navController: NavController) {
    var mapView by remember { mutableStateOf<MapView?>(null) }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .clipToBounds(),
        factory = { context ->
            MapView(context).apply {
                val prefs = context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
                Configuration.getInstance().load(context, prefs)
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                val centerGeoPoint = GeoPoint(41.6086, 21.7453)
                controller.setCenter(centerGeoPoint)
                controller.setZoom(9.0)

                mapView = this

                // Add markers for each event
                events.forEach { event ->
                    event.venue?.let { venue ->
                        venue.latitude?.let { lat ->
                            venue.longitude?.let { lon ->
                                val marker = Marker(this).apply {
                                    position = GeoPoint(lat, lon)
                                    title = event.title
                                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                    icon = mapView!!.context.getDrawable(R.drawable.location_on_24px)

                                    // Set custom info window
                                    infoWindow = CustomInfoWindow(mapView!!, event, venue, navController)
                                }

                                overlays.add(marker)
                            }
                        }
                    }
                }
                invalidate()
            }
        }
    )
}