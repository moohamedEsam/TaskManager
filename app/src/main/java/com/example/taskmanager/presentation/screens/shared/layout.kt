package com.example.taskmanager.presentation.screens.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.taskmanager.presentation.screens.reminderForm.navigateToReminderFormScreen

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
        topBar = {
            TopAppBar(
                title = { Text("Task Manager") },
                navigationIcon = {
                    NavigationIcon(navHostController)
                },
                actions = {
                    IconButton(onClick = navHostController::navigateToNoteFormScreen) {
                        Icon(Icons.Filled.NoteAdd, contentDescription = "Add note")
                    }

                    IconButton(onClick = navHostController::navigateToReminderFormScreen) {
                        Icon(Icons.Filled.AlarmAdd, contentDescription = "Add reminder")
                    }
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
