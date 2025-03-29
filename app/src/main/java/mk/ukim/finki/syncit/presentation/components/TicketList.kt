package mk.ukim.finki.syncit.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mk.ukim.finki.syncit.data.model.Ticket
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TicketList(tickets: List<Ticket>, navController: NavController) {
    if (tickets.isEmpty()) {
        Text(
            text = "No Tickets",
            modifier = Modifier
                .fillMaxSize()
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tickets) { ticket ->
                TicketCard(ticket, onClick = { navController.navigate("ticketDetails/${ticket.id}") })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}