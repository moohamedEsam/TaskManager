package com.example.taskmanager.presentation.screens.noteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteDetailsScreen(
    noteId: String,
    navHostController: NavHostController,
    viewModel: NoteDetailsViewModel = koinViewModel(parameters = { parametersOf(noteId) })
) {
    NoteScreenContent(viewModel = viewModel)
}

@Composable
fun NoteScreenContent(viewModel: NoteDetailsViewModel) {
    val note by viewModel.note.collectAsState()
    if (note.data == null) return
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(text = note.data?.title ?: "", style = MaterialTheme.typography.headlineMedium)
        }
        items(note.data?.body ?: emptyList()) {
            it.Draw(modifier = Modifier)
        }
    }
}
