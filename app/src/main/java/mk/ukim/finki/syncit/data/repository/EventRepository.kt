package mk.ukim.finki.syncit.data.repository

import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.remote.EventService

class EventRepository(private val eventService: EventService) {
    suspend fun fetchEvents(): List<Event> {
        return eventService.getEvents()
    }

    suspend fun addEvent(event: Event): Result<Unit> {
        return eventService .addEvent(event)
    }
}