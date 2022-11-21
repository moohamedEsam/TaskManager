package com.example.taskmanager.presentation.screens.notes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.taskmanager.presentation.navigation.Screens

fun Screens.notesScreenRoute() = "Notes"

fun NavGraphBuilder.notesFormScreen(
    onNoteClick: (String) -> Unit,
) {
    composable(Screens.notesScreenRoute()) {
        notesScreenRoute(onNoteClick)
    }
}

fun NavHostController.navigateToNotesScreen() {
    navigate(Screens.notesScreenRoute()) {
        launchSingleTop = true
    }
}