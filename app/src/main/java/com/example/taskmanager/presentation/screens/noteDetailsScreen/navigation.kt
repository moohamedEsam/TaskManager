package com.example.taskmanager.presentation.screens.noteDetailsScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.taskmanager.presentation.navigation.Screens

private const val NOTE_DETAILS_SCREEN_NAME = "noteDetailsScreen"

fun Screens.noteDetailsScreenRoute() = "$NOTE_DETAILS_SCREEN_NAME/{noteId}"


fun NavGraphBuilder.noteDetailsScreen(
    onEditClick: (String) -> Unit
) {
    composable(Screens.noteDetailsScreenRoute()) { backStackEntry ->
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