package com.example.taskmanager.presentation.screens.notes

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.taskmanager.presentation.navigation.Screens

fun Screens.notesScreenRoute() = "Notes"

fun NavGraphBuilder.notesFormScreen(
    snackbarHostState: SnackbarHostState,
    onNoteClick: (String) -> Unit,
) {
    composable(Screens.notesScreenRoute()) {
        notesScreenRoute(snackbarHostState, onNoteClick)
    }
}

fun NavHostController.navigateToNotesScreen() {
    navigate(Screens.notesScreenRoute()) {
        launchSingleTop = true
    }
}