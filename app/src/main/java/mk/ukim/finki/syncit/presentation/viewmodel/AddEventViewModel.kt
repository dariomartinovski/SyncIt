package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import mk.ukim.finki.syncit.data.model.Venue
import mk.ukim.finki.syncit.data.model.enums.Category
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import mk.ukim.finki.syncit.utils.toFormattedDate
import mk.ukim.finki.syncit.utils.toFormattedTime

class AddEventViewModel : ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var entryFee by mutableStateOf("")
    var selectedVenue by mutableStateOf<Venue?>(null)
    var selectedDate by mutableStateOf<Long?>(null)
    var selectedTime by mutableStateOf<Pair<Int, Int>?>(null)
    var selectedCategory by mutableStateOf<Category?>(null)

    fun saveEvent() {
        println(title)
        println(description)
        println(selectedVenue)
        println(entryFee)
        println(selectedDate?.toFormattedDate())
        println(selectedTime?.toFormattedTime())
        println(selectedCategory)
    }
}
