package com.example.taskmanager.presentation.screens.notes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.taskmanager.presentation.navigation.Screens

fun Screens.NotesScreenRoute() = "Notes"

fun NavGraphBuilder.notesFormScreen(
    onNoteClick: (String) -> Unit,
) {
    composable(Screens.NotesScreenRoute()) {
        NotesScreenRoute(onNoteClick)
    }
}

fun NavHostController.navigateToNotesScreen() {
    navigate(Screens.NotesScreenRoute())
}