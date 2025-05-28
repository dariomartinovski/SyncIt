package mk.ukim.finki.syncit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.data.model.Event
import java.time.LocalDateTime

class UpcomingEventsViewModel : ViewModel() {
    // 0 = Upcoming, 1 = History
    private val _createdTab = MutableStateFlow(0)
    val createdTab: StateFlow<Int> = _createdTab

    private val _createdEvents = MutableStateFlow<List<Event>>(MockData.events.take(5))
    val createdEvents: StateFlow<List<Event>> = _createdEvents

    fun setCreatedTab(index: Int) {
        _createdTab.value = index
    }

    fun getFilteredEvents(): List<Event> {
        return _createdEvents.value.filter {
            if (_createdTab.value == 0) it.startDateTimeParsed.isAfter(LocalDateTime.now())
            else it.startDateTimeParsed.isBefore(LocalDateTime.now())
        }
    }
}
