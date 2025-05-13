import androidx.lifecycle.ViewModel
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.data.model.Event
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class HomeViewModel : ViewModel() {
    private val _events = mutableStateOf<List<Event>>(emptyList())
    val events: State<List<Event>> = _events

    init {
        loadEvents()
    }

    private fun loadEvents() {
        // TODO Replace with API call or repository fetch
        _events.value = MockData.events
    }
}