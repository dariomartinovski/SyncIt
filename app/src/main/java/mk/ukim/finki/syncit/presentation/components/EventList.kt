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
import mk.ukim.finki.syncit.data.model.Event
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventList(events: List<Event>, navController: NavController) {
    if (events.isEmpty()) {
        Text(
            text = "No Events",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(events) { event ->
                EventCard(event, onClick = { navController.navigate("eventDetails/${event.id}") })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
