package com.example.taskmanager.presentation.screens.archivedNotes

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.taskmanager.presentation.screens.notes.NotesScreen
import com.example.taskmanager.presentation.utils.handleEvent
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArchivedNotesScreen(
    snackbarHostState: SnackbarHostState,
    onNoteClick: (String) -> Unit,
    viewModel: ArchivedNotesViewModel = koinViewModel()
) {
    val notes by viewModel.notes.collectAsState()
    val query by viewModel.query.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.receiveChannel.collectLatest {
            snackbarHostState.handleEvent(it)
        }
    }

    NotesScreen(
        notes = notes,
        query = query,
        onQueryChange = viewModel::setQuery,
        onFavoriteClick = {},
        onPinClick = {},
        onArchiveClick = viewModel::updateArchive,
        onNoteClick = onNoteClick
    )
}