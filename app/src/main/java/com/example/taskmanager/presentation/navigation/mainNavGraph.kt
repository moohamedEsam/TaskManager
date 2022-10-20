package com.example.taskmanager.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskmanager.presentation.screens.noteForm.NoteFormScreen
import com.example.taskmanager.presentation.screens.noteScreen.NoteDetailsScreen
import com.example.taskmanager.presentation.screens.notes.NotesScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.NotesScreen.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(vertical = 8.dp)
    ) {
        composable(Screens.NotesScreen.route) {
            NotesScreen(navHostController = navHostController)
        }

        composable("${Screens.CreateNoteScreen.route}/{noteId}") {
            val noteId = it.arguments?.getString("noteId") ?: "   "
            NoteFormScreen(
                navHostController = navHostController,
                snackbarHostState = snackbarHostState,
                noteId = noteId
            )
        }

        composable("${Screens.NoteDetailsScreen.route}/{noteId}") {
            val noteId = it.arguments?.getString("noteId") ?: "   "
            NoteDetailsScreen(noteId = noteId, navHostController = navHostController)
        }
    }
}