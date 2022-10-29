package com.example.taskmanager.presentation.screens.notes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.taskmanager.presentation.navigation.Screens

fun NavGraphBuilder.noteFormScreen(
    onNoteClick: (String) -> Unit,
) {
    composable(Screens.NotesScreen.route) {
        NotesScreen(onNoteClick)
    }
}

fun NavHostController.navigateToNotesScreen() {
    navigate(Screens.NotesScreen.route)
}