package com.example.taskmanager.presentation.screens.noteForm

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.taskmanager.presentation.navigation.Screens

private val NOTE_FORM_SCREEN_NAME = "Note Form"

fun Screens.noteFormScreenRoute() = "$NOTE_FORM_SCREEN_NAME/{noteId}"

fun NavGraphBuilder.noteFormScreen(
    snackbarHostState: SnackbarHostState,
    onNoteSaved: (String) -> Unit = {},
) {
    composable(Screens.noteFormScreenRoute()) { backStackEntry ->
        val noteId = backStackEntry.arguments?.getString("noteId") ?: "     "
        NoteFormScreen(
            noteId = noteId,
            snackbarHostState = snackbarHostState,
            onNoteSaved = onNoteSaved
        )
    }
}

fun NavHostController.navigateToNoteFormScreen(
    noteId: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate("$NOTE_FORM_SCREEN_NAME/$noteId") {
        builder()
    }
}

fun NavHostController.navigateToNoteFormScreen() {
    navigate("$NOTE_FORM_SCREEN_NAME/   "){
        launchSingleTop = true
    }
}