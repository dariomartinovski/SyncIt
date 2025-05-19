@file:OptIn(ExperimentalMaterial3Api::class)

package mk.ukim.finki.syncit.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ViewList
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mk.ukim.finki.syncit.navigation.BottomNavigationBar
import mk.ukim.finki.syncit.presentation.components.EventList
import mk.ukim.finki.syncit.presentation.components.EventsMap
import mk.ukim.finki.syncit.presentation.components.ExpandableFAB
import mk.ukim.finki.syncit.presentation.viewmodel.AuthViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.HomeViewModel
import mk.ukim.finki.syncit.presentation.viewmodel.factory.HomeViewModelFactory
import mk.ukim.finki.syncit.utils.TextUtils
import mk.ukim.finki.syncit.utils.TopBarUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())

    val isUserLoggedIn by authViewModel.isLoggedIn.collectAsState()
    var isListView by remember { mutableStateOf(true) }
    val events = viewModel.events

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarUtils.CustomTitle("SyncIt") },
                actions = {
                    TopBarUtils.CustomSuffixIconButton("Toggle View",
                        if (isListView) Icons.Default.Map else Icons.AutoMirrored.Outlined.ViewList,
                        { isListView = !isListView })
                    TopBarUtils.CustomLoginLogoutIconButton(navController, isUserLoggedIn)
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
                TextUtils.LargeTitle("Events")

                Spacer(modifier = Modifier.height(16.dp))

                if (isListView) {
                    EventList(events = events.value, navController = navController)
                } else {
                    EventsMap(events = events.value, navController = navController)
                }
            }
        }
    }
}
