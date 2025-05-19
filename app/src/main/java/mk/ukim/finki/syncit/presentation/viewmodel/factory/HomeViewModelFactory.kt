package mk.ukim.finki.syncit.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import mk.ukim.finki.syncit.data.remote.EventService
import mk.ukim.finki.syncit.data.repository.EventRepository
import mk.ukim.finki.syncit.presentation.viewmodel.HomeViewModel

class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val firestore = FirebaseFirestore.getInstance()
        val service = EventService(firestore)
        val repository = EventRepository(service)
        return HomeViewModel(repository) as T
    }
}