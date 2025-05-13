package mk.ukim.finki.syncit.data.mock

import mk.ukim.finki.syncit.data.model.enums.Category
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.model.Ticket
import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.data.model.Venue
import mk.ukim.finki.syncit.data.model.generateUniqueCode
import java.time.LocalDateTime

object MockData {
    val users = listOf(
        User(
            id = "user1",
            firstName = "Aleksandar",
            lastName = "Petkov",
            phoneNumber = "+38970123456",
            email = "aleksandar@example.com"
        ),
        User(
            id = "user2",
            firstName = "Elena",
            lastName = "Trajkovska",
            phoneNumber = "+38971234567",
            email = "elena@example.com"
        ),
        User(
            id = "user3",
            firstName = "Marko",
            lastName = "Jovanov",
            phoneNumber = "+38975234567",
            email = "marko@example.com"
        )
    )

    val venues = listOf(
        Venue(
            id = "venue1",
            title = "Mavrovo Ski Resort",
            address = "Mavrovo",
            latitude = 41.7006,
            longitude = 20.7411,
            description = "Popular winter sports destination",
            maxCapacity = 100,
            eventIds = listOf("event1", "event2")
        ),
        Venue(
            id = "venue2",
            title = "City Park Skopje",
            address = "Skopje",
            latitude = 41.9973,
            longitude = 21.4254,
            description = "Green park in the center of Skopje",
            maxCapacity = 100,
            eventIds = listOf("event3")
        ),
        Venue(
            id = "venue3",
            title = "Ohrid Amphitheater",
            address = "Ohrid",
            latitude = 41.1132,
            longitude = 20.8016,
            description = "Historic open-air theater with lake views",
            maxCapacity = 100,
            eventIds = listOf("event4", "event5")
        )
    )


    val events = listOf(
        Event(
            id = "event1",
            title = "Skiing Competition",
            description = "Annual skiing championship",
            host = users[0],
            venue = venues[1],
            startTime = LocalDateTime.of(2025, 3, 10, 10, 0),
            category = Category.WINTER,
            entryFee = 500,
            participants = listOf("user1", "user2")
        ),

        Event(
            id = "event2",
            title = "Snowboarding Workshop",
            description = "Learn snowboarding with experts",
            host = users[1],
            venue = venues[1],
            startTime = LocalDateTime.of(2025, 3, 15, 12, 0),
            category = Category.WINTER,
            entryFee = 500,
            participants = listOf("user3")
        ),

        Event(
            id = "event3",
            title = "Music Festival",
            description = "Live music performances in the park",
            host = users[2],
            venue = venues[2],
            startTime = LocalDateTime.of(2025, 6, 20, 18, 0),
            category = Category.FESTIVAL,
            entryFee = 500,
            participants = listOf("user1", "user3")
        ),

        Event(
            id = "event4",
            title = "Theater Night",
            description = "Classical drama performance",
            host = users[0],
            venue = venues[0],
            startTime = LocalDateTime.of(2025, 8, 5, 20, 0),
            category = Category.NIGHT,
            entryFee = 500,
            participants = listOf("user2")
        ),

        Event(
            id = "event5",
            title = "Ohrid Jazz Festival",
            description = "International jazz artists performing",
            host = users[2],
            venue = venues[2],
            startTime = LocalDateTime.of(2025, 8, 10, 19, 30),
            category = Category.FESTIVAL,
            entryFee = 500,
            participants = listOf("user1", "user2", "user3")
        ),
        Event(
            id = "event6",
            title = "Skiing Competition",
            description = "Annual skiing championship",
            host = users[1],
            venue = venues[1],
            startTime = LocalDateTime.of(2025, 3, 10, 10, 0),
            category = Category.WINTER,
            entryFee = 400,
            participants = listOf("user1", "user2")
        ),

        Event(
            id = "event7",
            title = "Snowboarding Workshop",
            description = "Learn snowboarding with experts",
            host = users[1],
            venue = venues[1],
            startTime = LocalDateTime.of(2025, 3, 15, 12, 0),
            category = Category.WINTER,
            entryFee = 200,
            participants = listOf("user3")
        ),

        Event(
            id = "event8",
            title = "Music Festival",
            description = "Live music performances in the park",
            host = users[2],
            venue = venues[2],
            startTime = LocalDateTime.of(2025, 6, 20, 18, 0),
            category = Category.FESTIVAL,
            entryFee = 700,
            participants = listOf("user1", "user3")
        ),

        Event(
            id = "event9",
            title = "Theater Night",
            description = "Classical drama performance",
            host = users[0],
            venue = venues[0],
            startTime = LocalDateTime.of(2025, 8, 5, 20, 0),
            category = Category.WINTER,
            entryFee = 600,
            participants = listOf("user2")
        ),

        Event(
            id = "event10",
            title = "Ohrid Jazz Festival",
            description = "International jazz artists performing",
            host = users[2],
            venue = venues[2],
            startTime = LocalDateTime.of(2025, 8, 10, 19, 30),
            category = Category.FESTIVAL,
            entryFee = 500,
            participants = listOf("user1", "user2", "user3")
        )
    )
    val tickets = listOf(
        Ticket(
            id = "ticket1",
            user = users[0],
            event = events[0],
            uniqueCode = generateUniqueCode()
        ),
        Ticket(
            id = "ticket2",
            user = users[1],
            event = events[1],
            uniqueCode = generateUniqueCode()
        ),
        Ticket(
            id = "ticket3",
            user = users[2],
            event = events[2],
            uniqueCode = generateUniqueCode()
        ),
        Ticket(
            id = "ticket4",
            user = users[0],
            event = events[3],
            uniqueCode = generateUniqueCode()
        ),
        Ticket(
            id = "ticket5",
            user = users[1],
            event = events[4],
            uniqueCode = generateUniqueCode()
        )
    )
}




