package mk.ukim.finki.syncit.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mk.ukim.finki.syncit.data.model.Event

class EventService(private val firestore: FirebaseFirestore) {

    suspend fun getEvents(): List<Event> {
        return try {
            val snapshot = firestore.collection("events").get().await()
            snapshot.documents.mapNotNull { it.toObject(Event::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getEventById(eventId: String): Event? {
        return try {
            val snapshot = firestore.collection("events").document(eventId).get().await()
            snapshot.toObject(Event::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun addEvent(event: Event): Result<Unit> {
        return try {
            val newDoc = firestore.collection("events").document()
            val eventWithId = event.copy(id = newDoc.id)
            newDoc.set(eventWithId).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}