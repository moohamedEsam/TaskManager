package com.example.taskmanager.presentation.screens.noteDetailsScreen

import android.content.Intent
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.taskmanager.presentation.navigation.Screens

private const val NOTE_DETAILS_SCREEN_NAME = "Note Details"

fun NavGraphBuilder.noteDetailsScreen(
    onEditClick: (String) -> Unit
) {
    composable("$NOTE_DETAILS_SCREEN_NAME/{noteId}") { backStackEntry ->
        val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
        NoteDetailsScreen(noteId = noteId, onEditClick = onEditClick)
    }
}

fun NavHostController.navigateToNoteDetailsScreen(
    noteId: String,
    options: NavOptionsBuilder.() -> Unit = {}
) {
    navigate("$NOTE_DETAILS_SCREEN_NAME/$noteId") {
        options()
    }
}