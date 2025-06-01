package mk.ukim.finki.syncit.utils

import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.model.Ticket

object EmailContentHelper {
    fun getTicketConfirmationBody(ticket: Ticket, event: Event): String {
        val qrBitmap = QRCodeGenerator.generateQrCode(ticket.uniqueCode)
        val qrBase64 = qrBitmap?.let { QRCodeGenerator.bitmapToBase64(it) }

        val qrImageTag = if (qrBase64 != null) {
            """<img src="data:image/png;base64,$qrBase64" alt="QR Code" style="width:200px; height:200px;"/>"""
        } else {
            "<p><em>QR Code could not be generated.</em></p>"
        }

        return """
            <h2>Thank you for your purchase!</h2>
            <p>You’ve successfully reserved a ticket for <strong>${event.title}</strong>.</p>
            <p><strong>Location:</strong> ${event.venue.title}, ${event.venue.address}</p>
            <p><strong>Date:</strong> ${event.startDateTimeParsed}</p>
            <p>Show this code at the entrance to validate your ticket.</p>
            <p>Your QR Code:</p>
            $qrImageTag
            <p><strong>Ticket Code:</strong> ${ticket.uniqueCode}</p>
        """.trimIndent()
    }

    fun getBulkTicketConfirmationBody(tickets: List<Ticket>, event: Event): String {
        val ticketRows = tickets.joinToString("\n") { ticket ->
            val qrCodeBase64 = QRCodeGenerator.generateQRCodeBase64(ticket.uniqueCode)
            """
        <div style="margin-bottom:20px;">
            <h4>Ticket Code: ${ticket.uniqueCode}</h4>
            <img src="data:image/png;base64,$qrCodeBase64" alt="QR Code" width="200" height="200" />
        </div>
        """.trimIndent()
        }

        return """
        <html>
        <body style="font-family:sans-serif;">
            <h2>Thank you for your purchase, ${tickets.first().user.firstName}!</h2>
            <p>You have successfully reserved <strong>${tickets.size}</strong> ticket(s) for <strong>${event.title}</strong>.</p>
            <p><strong>Date:</strong> ${event.startDateTimeParsed.toSimpleFormat()}</p>
            <p>Please present the following QR codes at the entrance:</p>
            $ticketRows
            <br/>
            <p>Best regards,<br/>SyncIt Team</p>
        </body>
        </html>
    """.trimIndent()
    }
}