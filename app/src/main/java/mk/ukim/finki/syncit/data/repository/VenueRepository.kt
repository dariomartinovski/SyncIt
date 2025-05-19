package mk.ukim.finki.syncit.data.repository

import mk.ukim.finki.syncit.data.model.Venue
import mk.ukim.finki.syncit.data.remote.VenueService

class VenueRepository(private val venueService: VenueService) {

    suspend fun getAllVenues(): Result<List<Venue>> {
        return venueService.getAllVenues()
    }

    suspend fun addVenue(venue: Venue): Result<Unit> {
        return venueService.addVenue(venue)
    }
}