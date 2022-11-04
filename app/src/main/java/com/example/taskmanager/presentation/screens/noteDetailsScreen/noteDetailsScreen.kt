package com.example.taskmanager.presentation.screens.noteDetailsScreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taskmanager.R
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.NoteWithTags
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteDetailsScreen(
    noteId: String,
    onEditClick: (String) -> Unit = {},
    viewModel: NoteDetailsViewModel = koinViewModel(parameters = { parametersOf(noteId) })
) {
    val note by viewModel.note.collectAsState()

    NoteDetailsScreen(
        uiState = note,
        onEditClick = onEditClick,
        onDeleteClick = viewModel::onDeleteClick,
        onFavoriteClick = viewModel::onFavoriteClick,
        onPinClick = viewModel::onPinClick,
    )
}

@Composable
fun NoteDetailsScreen(
    uiState: Resource<NoteWithTags>,
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onPinClick: () -> Unit = {},
) {
    when (uiState) {
        is Resource.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.message ?: "something went wrong",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        is Resource.Initial -> Unit
        is Resource.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        is Resource.Success -> NoteScreenContent(
            uiState = uiState,
            onEditClick = onEditClick,
            onFavoriteClick = onFavoriteClick,
            onDeleteClick = onDeleteClick,
            onPinClick = onPinClick
        )
    }
}

@Composable
private fun NoteScreenContent(
    uiState: Resource<NoteWithTags>,
    onEditClick: (String) -> Unit,
    onFavoriteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onPinClick: () -> Unit
) {
    if (uiState.data == null) return
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ActionBar(
                note = uiState.data,
                onEditClick = { onEditClick(uiState.data.noteId) },
                onDeleteClick = onDeleteClick,
                onFavoriteClick = onFavoriteClick,
                onPinClick = onPinClick,
            )
        }

        items(uiState.data.body) {
            it.Draw(modifier = Modifier)
        }
    }
}

@Composable
private fun ActionBar(
    note: NoteWithTags,
    onEditClick: () -> Unit,
    onPinClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = note.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.weight(0.8f))

        IconButton(onClick = onEditClick) {
            Icon(Icons.Outlined.Edit, contentDescription = "edit")
        }
        IconButton(onClick = onPinClick) {
            Icon(
                painter = if (note.isPinned)
                    painterResource(id = R.drawable.pin_filled)
                else
                    painterResource(id = R.drawable.pin),
                contentDescription = "pin",
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(onClick = onFavoriteClick) {
            if (note.isFavorite)
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "favorite")
            else
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "favorite")
        }
        IconButton(onClick = onDeleteClick) {
            if (note.isDeleted)
                Icon(Icons.Filled.Delete, contentDescription = "delete")
            else
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "delete")
        }

    }
}
