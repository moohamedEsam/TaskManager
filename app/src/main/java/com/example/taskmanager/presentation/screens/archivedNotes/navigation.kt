package com.example.taskmanager.presentation.screens.archivedNotes

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.taskmanager.presentation.navigation.Screens

fun Screens.archivedNotesScreenRoute() = "Archived Notes"

fun NavGraphBuilder.archivedNotesScreen(
    snackbarHostState: SnackbarHostState,
    onNoteClick: (String) -> Unit,
) {
    composable(Screens.archivedNotesScreenRoute()) {
        ArchivedNotesScreen(snackbarHostState, onNoteClick)
    }
}

fun NavHostController.navigateToArchivedNotesScreen() {
    navigate(Screens.archivedNotesScreenRoute()) {
        launchSingleTop = true
    }
}