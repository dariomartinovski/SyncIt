@file:OptIn(ExperimentalMaterial3Api::class)
package mk.ukim.finki.syncit.presentation.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mk.ukim.finki.syncit.R
import mk.ukim.finki.syncit.domain.services.GeocodingService
import mk.ukim.finki.syncit.presentation.viewmodel.AddVenueViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.AuthViewModel
import mk.ukim.finki.syncit.utils.TextUtils
import mk.ukim.finki.syncit.utils.TopBarUtils
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@SuppressLint("ClickableViewAccessibility")
@Composable
fun AddVenueScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    viewModel: AddVenueViewModel = viewModel()
) {
    val isUserLoggedIn by authViewModel.isLoggedIn.collectAsState()

    val title = viewModel.title
    val description = viewModel.description
    val maxCapacity = viewModel.maxCapacity
    val location = viewModel.location
    val latitude = viewModel.latitude
    val longitude = viewModel.longitude

    var mapView by remember { mutableStateOf<MapView?>(null) }
    var marker by remember { mutableStateOf<Marker?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val SELECTED_POINT_MAP_ZOOM = 16.0
    val SELECTED_POINT_MAP_ZOOM_SPEED = 2L

    fun setLatitudeAndLongitudeFromGeoPoint(point: GeoPoint) {
        viewModel.latitude = point.latitude
        viewModel.longitude = point.longitude
    }

    fun onLocationSelected(lat: Double, lon: Double) {
        viewModel.location = "Fetching address..."
        coroutineScope.launch {
            val address = withContext(Dispatchers.IO) {
                GeocodingService.getAddressFromCoordinates(lat, lon)
            }
            val centerGeoPoint = GeoPoint(lat, lon)
            setLatitudeAndLongitudeFromGeoPoint(centerGeoPoint)
            mapView?.controller?.setCenter(centerGeoPoint)
            mapView?.controller?.setZoom(SELECTED_POINT_MAP_ZOOM)
            viewModel.location = address ?: "Unknown Location"
        }
    }

    fun updateLocationOnMap(address: String) {
        coroutineScope.launch {
            val geoPoint = withContext(Dispatchers.IO) {
                GeocodingService.getCoordinatesFromAddress(address)
            }
            geoPoint?.let { point ->
                setLatitudeAndLongitudeFromGeoPoint(point)
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

                                viewModel.title = "Selected Location"
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("Add Venue") },
                navigationIcon = { TopBarUtils.CustomBackAction(navController) },
                actions = { TopBarUtils.CustomLoginLogoutIconButton(navController, isUserLoggedIn) },
                colors = TopBarUtils.CustomBackground(),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            TextUtils.LargeTitle("Add Venue")

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { viewModel.title = it },
                label = { Text("Venue Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { viewModel.description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = maxCapacity,
                onValueChange = { viewModel.maxCapacity = it },
                label = { Text("Max Capacity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { viewModel.location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        if (location.isNotEmpty()) {
                            updateLocationOnMap(location)
                        }
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Search Location")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
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

                        // Initialize marker
                        marker = Marker(this).apply {
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            icon = context.getDrawable(R.drawable.location_on_24px)
                        }
                        this.overlays.add(marker)

                        // Add touch listener for tap events
                        this.overlays.add(object : org.osmdroid.views.overlay.Overlay() {
                            override fun onSingleTapConfirmed(e: android.view.MotionEvent?, mapView: MapView?): Boolean {
                                e?.let {
                                    val geoPoint = mapView?.projection?.fromPixels(e.x.toInt(), e.y.toInt()) as GeoPoint
                                    onLocationSelected(geoPoint.latitude, geoPoint.longitude)

                                    // Update marker position
                                    marker?.position = geoPoint
                                    mapView.invalidate()
                                }
                                return true
                            }
                        })
                    }
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    viewModel.saveVenue()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Venue")
            }
        }
    }
}