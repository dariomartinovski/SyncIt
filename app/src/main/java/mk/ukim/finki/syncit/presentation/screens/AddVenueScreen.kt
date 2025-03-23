@file:OptIn(ExperimentalMaterial3Api::class)
package mk.ukim.finki.syncit.presentation.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mk.ukim.finki.syncit.R
import mk.ukim.finki.syncit.domain.services.GeocodingService
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun AddVenueScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var maxCapacity by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var marker by remember { mutableStateOf<Marker?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val SELECTED_POINT_MAP_ZOOM = 16.0
    val SELECTED_POINT_MAP_ZOOM_SPEED = 2L

    fun onLocationSelected(lat: Double, lon: Double) {
        location = "Fetching address..."
        coroutineScope.launch {
            val address = GeocodingService.getAddressFromCoordinates(lat, lon)
            location = address ?: "Unknown Location"
            mapView?.controller?.setCenter(GeoPoint(lat, lon))
            marker?.position = GeoPoint(lat, lon)
        }
    }

    fun updateLocationOnMap(address: String) {
        coroutineScope.launch {
            val geoPoint = withContext(Dispatchers.IO) {
                GeocodingService.getCoordinatesFromAddress(address)
            }
            geoPoint?.let { point ->
                withContext(Dispatchers.Main) {
                    mapView?.let { map ->
                        map.controller.animateTo(
                            point,
                            SELECTED_POINT_MAP_ZOOM,
                            SELECTED_POINT_MAP_ZOOM_SPEED
                        )

                        if (marker == null) {
                            marker = Marker(map).apply {
                                position = point

                                title = "Selected Location"
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                icon = mapView!!.context.getDrawable(R.drawable.location_on_24px)
                            }
                            map.overlays.add(marker!!)

                        } else {
                            marker!!.position = point
                            marker!!.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        }

                        map.invalidate()
                    }
                }
            }
        }
    }

    fun _saveVenue() {
        //TODO write code here
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Venue") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Venue Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = maxCapacity,
                onValueChange = { maxCapacity = it },
                label = { Text("Max Capacity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clipToBounds(),
                    factory = { context ->
                        // Initialize OSM map
                        MapView(context).apply {
                            val prefs = context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
                            Configuration.getInstance().load(context, prefs)
                            setTileSource(TileSourceFactory.MAPNIK)
                            setMultiTouchControls(true)

                            val centerGeoPoint = GeoPoint(41.6086, 21.7453)
                            controller.setCenter(centerGeoPoint)
                            controller.setZoom(9.0)

                            mapView = this
                        }
                    },
                    update = { map ->
                        map?.let {
                            if (location.isNotEmpty()) {
                                updateLocationOnMap(location)
                            }
                        }
                    }
                )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    _saveVenue()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Venue")
            }
        }
    }
}