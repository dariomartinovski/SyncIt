package mk.ukim.finki.syncit.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.utils.CategoryUtils
import mk.ukim.finki.syncit.utils.toSimpleFormat

@Composable
fun EventCard(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = CategoryUtils.getCategoryIcon(event.category),
                contentDescription = event.category.name,
                modifier = Modifier
                    .padding(start = 15.dp, end = 32.dp)
                    .align(Alignment.CenterVertically)
                    .size(25.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Box(Modifier.height(5.dp))
                Text(
                    text = "📍 ${event.venue.title}",
                    style = MaterialTheme.typography.bodySmall
                )
                Box(Modifier.height(5.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "📅 ${event.startDateTimeParsed.toSimpleFormat()}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$${event.entryFee} MKD",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}