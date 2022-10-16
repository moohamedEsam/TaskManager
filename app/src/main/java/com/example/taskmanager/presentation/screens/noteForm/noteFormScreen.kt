package com.example.taskmanager.presentation.screens.noteForm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.taskmanager.presentation.utils.noteBody.ListProvider
import com.example.taskmanager.presentation.utils.noteBody.TextProvider
import com.example.taskmanager.ui.theme.TaskManagerTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteFormScreen(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    noteId: String,
    viewModel: NoteFormViewModel = koinViewModel(parameters = { parametersOf(noteId) })
) {
    NoteFormScreenContent()
}

@Composable
fun NoteFormScreenContent() {
    Column(modifier = Modifier.fillMaxSize()) {
    }
}

@Preview
@Composable
fun NoteFormPreview() {
    TaskManagerTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ListProvider.Draw(modifier = Modifier.fillMaxWidth().height(400.dp), onAdd = {}) {}
        }
    }
}