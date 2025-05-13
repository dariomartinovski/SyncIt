package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BuyTicketsViewModelFactory(private val eventId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuyTicketsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BuyTicketsViewModel(eventId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}