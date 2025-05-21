package mk.ukim.finki.syncit.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.data.model.Venue
import mk.ukim.finki.syncit.data.model.enums.Category
import java.time.LocalDateTime

class EventService(private val firestore: FirebaseFirestore) {

    suspend fun getEvents(): List<Event> {
        return try {
            val snapshot = firestore.collection("events").get().await()
            snapshot.documents.mapNotNull { it.toObject(Event::class.java) }
        } catch (e: Exception) {
            //TODO maybe modify the error later
            emptyList()
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

    fun seedExampleEvent(firestore: FirebaseFirestore) {
        val event = Event(
            id = "event123",
            title = "Spring Music Festival",
            description = "Join us for a night of music, food, and fun.",
            category = Category.FESTIVAL,
            host = User(
                id = "user123",
                firstName = "Alice",
                lastName = "Smith",
                phoneNumber = "123-456-7890",
                email = "alice@example.com"
            ),
            venue = Venue(
                id = "venue123",
                title = "Central Park Amphitheater",
                description = "Outdoor venue perfect for events.",
                maxCapacity = 5000,
                address = "123 Park Ave, New York, NY",
                latitude = 40.785091,
                longitude = -73.968285
            ),
            entryFee = 25,
            startTime = LocalDateTime.of(2025, 6, 10, 19, 0),
            participants = listOf("user123", "user456")
        )

        firestore.collection("events").document(event.id).set(event)
            .addOnSuccessListener { println("Event seeded successfully!") }
            .addOnFailureListener { e -> println("Error seeding event: ${e.message}") }
    }

}