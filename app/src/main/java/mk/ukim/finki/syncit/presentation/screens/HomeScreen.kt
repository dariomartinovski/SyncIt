@file:OptIn(ExperimentalMaterial3Api::class)

package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ViewList
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.unit.dp
import mk.ukim.finki.syncit.data.mock.MockData
import mk.ukim.finki.syncit.navigation.BottomNavigationBar
import mk.ukim.finki.syncit.presentation.components.EventList
import mk.ukim.finki.syncit.presentation.components.EventsMap
import mk.ukim.finki.syncit.presentation.components.ExpandableFAB
import mk.ukim.finki.syncit.utils.TopBarUtils


@Composable
fun HomeScreen(navController: NavController) {
    var isListView by remember { mutableStateOf(true) }
    val events = MockData.events

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("SyncIt") },
                actions = {
                    TopBarUtils.CustomSuffixIconButton("Toggle View",
                        if (isListView) Icons.Default.Map else Icons.AutoMirrored.Outlined.ViewList,
                        { isListView = !isListView })
                    TopBarUtils.CustomLoginLogoutIconButton(navController)
                },
                colors = TopBarUtils.CustomBackground(),
            )
        },
        floatingActionButton = {
            ExpandableFAB(navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Column {
                Text("Events")
                if (isListView) {
                    EventList(events = events, navController = navController)
                } else {
                    EventsMap(events = events, navController = navController)
                }
            }
        }
    }
}
