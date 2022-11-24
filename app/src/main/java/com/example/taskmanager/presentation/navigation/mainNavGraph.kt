package com.example.taskmanager.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.taskmanager.presentation.screens.archivedNotes.archivedNotesScreen
import com.example.taskmanager.presentation.screens.noteDetailsScreen.navigateToNoteDetailsScreen
import com.example.taskmanager.presentation.screens.noteDetailsScreen.noteDetailsScreen
import com.example.taskmanager.presentation.screens.noteForm.navigateToNoteFormScreen
import com.example.taskmanager.presentation.screens.noteForm.noteFormScreen
import com.example.taskmanager.presentation.screens.notes.notesFormScreen
import com.example.taskmanager.presentation.screens.notes.notesScreenRoute
import com.example.taskmanager.presentation.screens.reminderDetailsScreen.navigateToReminderDetailsScreen
import com.example.taskmanager.presentation.screens.reminderDetailsScreen.reminderDetailsScreen
import com.example.taskmanager.presentation.screens.reminderForm.reminderFormScreen
import com.example.taskmanager.presentation.screens.reminders.remindersScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.notesScreenRoute(),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        notesFormScreen(snackbarHostState) {
            navHostController.navigateToNoteDetailsScreen(it)
        }

        archivedNotesScreen(snackbarHostState) {
            navHostController.navigateToNoteDetailsScreen(it)
        }

        noteFormScreen(snackbarHostState) { id ->
            navHostController.navigateToNoteDetailsScreen(id) {
                navHostController.popBackStack()
            }
        }

        noteDetailsScreen { id ->
            navHostController.navigateToNoteFormScreen(id) {
                navHostController.popBackStack()
            }
        }

        reminderFormScreen(snackbarHostState) {
            navHostController.navigateToReminderDetailsScreen(it)
        }

        reminderDetailsScreen(snackbarHostState)

        remindersScreen {
            navHostController.navigateToReminderDetailsScreen(it)
        }
    }
}