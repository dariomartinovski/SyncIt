package mk.ukim.finki.syncit.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.model.Ticket
import mk.ukim.finki.syncit.data.model.User
import mk.ukim.finki.syncit.utils.EmailContentHelper
import mk.ukim.finki.syncit.utils.EmailSender
import mk.ukim.finki.syncit.utils.QRCodeGenerator

class TicketService(private val db: FirebaseFirestore) {

    suspend fun reserveTickets(
        user: User,
        event: Event,
        quantity: Int
    ): Result<Unit> {
        return try {
            val ticketsCollection = db.collection("tickets")
            val reservedTickets = mutableListOf<Ticket>()
            val qrImages = mutableListOf<Pair<String, ByteArray>>()

            repeat(quantity) { index ->
                val ticketId = ticketsCollection.document().id
                val uniqueCode = QRCodeGenerator.generateUniqueCode()
                val ticket = Ticket(
                    id = ticketId,
                    user = user,
                    event = event,
                    uniqueCode = uniqueCode
                )

                ticketsCollection.document(ticketId).set(ticket).await()
                reservedTickets.add(ticket)

                val bitmap = QRCodeGenerator.generateQrCode(uniqueCode)
                val byteArray = QRCodeGenerator.bitmapToPngBytes(bitmap!!)
                val cid = "qr${index + 1}"
                qrImages.add(cid to byteArray)
            }

            withContext(Dispatchers.IO) {
                val emailBody = EmailContentHelper.getBulkTicketConfirmationBodyWithCids(
                    reservedTickets,
                    event
                )
                EmailSender.sendEmailWithQrAttachment(
                    toEmail = user.email,
                    subject = "Your Tickets for ${event.title}",
                    htmlBody = emailBody,
                    qrImages = qrImages
                )
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getTickets(): List<Ticket> {
        return try {
            val snapshot = db.collection("tickets").get().await()
            snapshot.documents.mapNotNull { it.toObject(Ticket::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getTicketsForUser(userId: String): List<Ticket> {
        return try {
            val snapshot = db.collection("tickets")
                .whereEqualTo("user.id", userId)
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(Ticket::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getTicketById(ticketId: String): Ticket? {
        return try {
            val snapshot = db.collection("tickets").document(ticketId).get().await()
            snapshot.toObject(Ticket::class.java)
        } catch (e: Exception) {
            null
        }
    }

}