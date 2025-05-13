package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class AddVenueViewModel : ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var maxCapacity by mutableStateOf("")
    var location by mutableStateOf("")
    var latitude by mutableStateOf<Double?>(null)
    var longitude by mutableStateOf<Double?>(null)

    fun saveVenue() {
        // TODO: Implement the logic to save the venue to a database or backend
        println("Venue saved!")
        println("Title: $title")
        println("Description: $description")
        println("Max Capacity: $maxCapacity")
        println("Location: $location")
        println("Latitude: $latitude")
        println("Longitude: $longitude")
    }
}
