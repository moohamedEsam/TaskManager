package com.example.taskmanager.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.taskmanager.presentation.screens.noteDetailsScreen.navigateToNoteDetailsScreen
import com.example.taskmanager.presentation.screens.noteDetailsScreen.noteDetailsScreen
import com.example.taskmanager.presentation.screens.noteForm.navigateToNoteFormScreen
import com.example.taskmanager.presentation.screens.noteForm.noteFormScreen
import com.example.taskmanager.presentation.screens.notes.NotesScreenRoute
import com.example.taskmanager.presentation.screens.notes.notesFormScreen
import com.example.taskmanager.presentation.screens.reminderForm.ReminderFormScreen
import com.example.taskmanager.presentation.screens.reminderForm.reminderFormScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.NotesScreenRoute(),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(vertical = 8.dp)
    ) {

        notesFormScreen {
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

        reminderFormScreen(snackbarHostState){

        }
    }
}