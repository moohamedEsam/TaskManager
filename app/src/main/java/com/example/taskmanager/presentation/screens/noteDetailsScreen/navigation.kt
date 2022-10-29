package com.example.taskmanager.presentation.screens.noteDetailsScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.noteDetailsScreen(
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit
) {
    composable("note_details/{noteId}") { backStackEntry ->
        val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
        NoteDetailsScreen(noteId = noteId, onBackClick = onBackClick, onEditClick = onEditClick)
    }
}

fun NavHostController.navigateToNoteDetailsScreen(noteId: String) {
    navigate("note_details/$noteId")
}