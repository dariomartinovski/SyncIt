package mk.ukim.finki.syncit.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ExpandableFAB(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.BottomEnd)) {
        Column(horizontalAlignment = Alignment.End) {
            if (expanded) {
                FloatingActionButton(
                    onClick = { navController.navigate("addVenue") }
                ) {
                    Icon(imageVector = Icons.Default.MapsHomeWork, contentDescription = "Add Venue")
                }
                Spacer(modifier = Modifier.height(8.dp))

                FloatingActionButton(
                    onClick = { navController.navigate("addEvent") }
                ) {
                    Icon(imageVector = Icons.Default.Event, contentDescription = "Add Event")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            FloatingActionButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Expand Menu")
            }
        }
    }
}