package com.example.taskmanager.presentation.screens.noteForm

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.noteFormScreen(
    snackbarHostState: SnackbarHostState
) {
    composable("note_form/{noteId}") { backStackEntry ->
        val noteId = backStackEntry.arguments?.getString("noteId") ?: "     "
        NoteFormScreen(noteId = noteId, snackbarHostState = snackbarHostState)
    }
}

fun NavHostController.navigateToNoteFormScreen(noteId: String) {
    navigate("note_form/$noteId")
}