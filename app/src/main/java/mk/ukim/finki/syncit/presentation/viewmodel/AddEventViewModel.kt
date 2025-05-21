package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import mk.ukim.finki.syncit.data.model.*
import mk.ukim.finki.syncit.data.model.enums.Category
import mk.ukim.finki.syncit.data.remote.EventService
import mk.ukim.finki.syncit.data.remote.VenueService
import mk.ukim.finki.syncit.data.repository.EventRepository
import mk.ukim.finki.syncit.data.repository.VenueRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AddEventViewModel : ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var entryFee by mutableStateOf("")
    var selectedVenue by mutableStateOf<Venue?>(null)
    var selectedDate by mutableStateOf(LocalDate.now())
    var selectedTime by mutableStateOf(LocalTime.now())
    var selectedCategory by mutableStateOf<Category?>(null)

    var venues by mutableStateOf<List<Venue>>(emptyList())
    var eventSavedResult by mutableStateOf<Pair<Boolean, String?>>(false to null)

    private val venueRepository = VenueRepository(VenueService(FirebaseFirestore.getInstance()))
    private val eventRepository = EventRepository(EventService(FirebaseFirestore.getInstance()))

    init {
        fetchVenues()
    }

    private fun fetchVenues() {
        viewModelScope.launch {
            val result = venueRepository.getAllVenues()
            if (result.isSuccess) {
                venues = result.getOrDefault(emptyList())
            }
        }
    }

    fun saveEvent(currentUser: User, onResult: (success: Boolean, errorMessage: String?) -> Unit) {
        val venue = selectedVenue
        val category = selectedCategory
        val fee = entryFee.toLongOrNull()
        //TODO get the currentUser from the currentUserId or something similar....
//        val currentUser =

        if (title.isBlank() || description.isBlank() || venue == null || category == null || fee == null) {
            onResult(false, "All fields must be filled correctly.")
            return
        }

        val event = Event(
            id = "",
            title = title,
            description = description,
            category = category,
            host = currentUser,
            venue = venue,
            entryFee = fee,
            startTime = LocalDateTime.of(selectedDate, selectedTime),
            participants = emptyList()
        )

        viewModelScope.launch {
            val result = eventRepository.addEvent(event)
            onResult(result.isSuccess, result.exceptionOrNull()?.message)
        }
    }
}