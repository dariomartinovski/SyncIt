package mk.ukim.finki.syncit.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.model.Ticket
import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.utils.QRCodeGenerator

class TicketService(private val db: FirebaseFirestore) {

    suspend fun reserveTickets(
        user: User,
        event: Event,
        quantity: Int
    ): Result<Unit> {
        return try {
            val ticketsCollection = db.collection("tickets")

            repeat(quantity) {
                val ticketId = ticketsCollection.document().id
                val ticket = Ticket(
                    id = ticketId,
                    user = user,
                    event = event,
                    uniqueCode = QRCodeGenerator.generateUniqueCode()
                )
                ticketsCollection.document(ticketId).set(ticket).await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}