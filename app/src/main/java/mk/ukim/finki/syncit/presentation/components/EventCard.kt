package mk.ukim.finki.syncit.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.utils.toSimpleFormat

@Composable
fun EventCard(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "📅 ${event.startDateTimeParsed.toSimpleFormat()}", style = MaterialTheme.typography.bodySmall)
            Text(text = "📍 ${event.venue.title}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
