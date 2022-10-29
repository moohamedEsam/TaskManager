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
import com.example.taskmanager.presentation.screens.noteDetailsScreen.navigateToNoteDetailsScreen
import com.example.taskmanager.presentation.screens.noteDetailsScreen.noteDetailsScreen
import com.example.taskmanager.presentation.screens.noteForm.noteFormScreen
import com.example.taskmanager.presentation.screens.notes.NotesScreen
import com.example.taskmanager.presentation.screens.notes.noteFormScreen

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

        noteFormScreen {
            navHostController.navigateToNoteDetailsScreen(it)
        }

        noteFormScreen(snackbarHostState)

        noteDetailsScreen(
            onBackClick = navHostController::popBackStack,
            onEditClick = { id ->
                navHostController.navigateToNoteDetailsScreen(id)
            }
        )
    }
}