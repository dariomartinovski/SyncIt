package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.model.Venue
import mk.ukim.finki.syncit.data.remote.VenueService
import mk.ukim.finki.syncit.data.repository.VenueRepository

class AddVenueViewModel : ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var maxCapacity by mutableStateOf("")
    var location by mutableStateOf("")
    var latitude by mutableStateOf<Double?>(null)
    var longitude by mutableStateOf<Double?>(null)

    private val venueRepository = VenueRepository(VenueService(FirebaseFirestore.getInstance()))

    fun saveVenue(onResult: (Boolean, String?) -> Unit) {
        val capacity = maxCapacity.toLongOrNull()
        val lat = latitude
        val lon = longitude

        if (title.isBlank() || description.isBlank() ||
            capacity == null || location.isBlank() || lat == null || lon == null
        ) {
            onResult(false, "All fields are required and must be valid.")
            return
        }

        val venue = Venue(
            id = "",
            title = title,
            description = description,
            maxCapacity = capacity,
            address = location,
            latitude = lat,
            longitude = lon
        )

        viewModelScope.launch {
            val result = venueRepository.addVenue(venue)
            onResult(result.isSuccess, result.exceptionOrNull()?.message)
        }
    }
}