package mk.ukim.finki.syncit.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mk.ukim.finki.syncit.data.model.Venue
import java.util.UUID

class VenueService(private val firestore: FirebaseFirestore) {

    suspend fun getAllVenues(): Result<List<Venue>> {
        return try {
            val snapshot = firestore.collection("venues").get().await()
            val venues = snapshot.documents.mapNotNull { it.toObject(Venue::class.java)?.copy(id = it.id) }
            Result.success(venues)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addVenue(venue: Venue): Result<Unit> {
        return try {
            val venueId = UUID.randomUUID().toString()
            val newVenue = venue.copy(id = venueId)
            firestore.collection("venues").document(venueId).set(newVenue).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}