package com.example.taskmanager.presentation.screens.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.presentation.navigation.Navigation
import com.example.taskmanager.presentation.navigation.Screens
import com.example.taskmanager.presentation.screens.noteForm.navigateToNoteFormScreen
import com.example.taskmanager.presentation.screens.notes.NotesScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout() {
    val navHostController = rememberNavController()
    val snackbarHostState by remember {
        mutableStateOf(SnackbarHostState())
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = {
            Navigation(
                navHostController = navHostController,
                snackbarHostState = snackbarHostState,
                paddingValues = it
            )
        },
        floatingActionButton = {
            CreateNoteFloatingButton(navHostController)
        },
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            TopAppBar(
                title = { Text("Task Manager") },
                navigationIcon = {
                    NavigationIcon(navHostController)
                }
            )
        }
    )
}

@Composable
private fun NavigationIcon(navHostController: NavHostController) {
    val currentRoute by navHostController.currentBackStackEntryAsState()
    if (currentRoute?.destination?.route != Screens.NotesScreenRoute())
        IconButton(onClick = navHostController::popBackStack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
    else
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null
            )
        }
}

@Composable
private fun CreateNoteFloatingButton(navHostController: NavHostController) {
    val currentDestination by navHostController.currentBackStackEntryAsState()
    if (currentDestination?.destination?.route != Screens.NotesScreenRoute()) return
    FloatingActionButton(
        onClick = navHostController::navigateToNoteFormScreen
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}
