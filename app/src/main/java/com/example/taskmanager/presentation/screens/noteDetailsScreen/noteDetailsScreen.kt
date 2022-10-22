package com.example.taskmanager.presentation.screens.noteDetailsScreen

import android.app.ActionBar
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.taskmanager.R
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteDetailsScreen(
    noteId: String,
    onBackClick: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    viewModel: NoteDetailsViewModel = koinViewModel(parameters = { parametersOf(noteId) })
) {
    NoteScreenContent(viewModel = viewModel, onBackClick = onBackClick, onEditClick = onEditClick)
}

@Composable
fun NoteScreenContent(
    viewModel: NoteDetailsViewModel,
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit
) {
    val note by viewModel.note.collectAsState()
    if (note.data == null) return
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ActionBar(
                noteTitle = note.data?.title ?: "",
                onBackClick = onBackClick,
                onEditClick = { onEditClick(note.data?.noteId ?: "  ") }
            )
        }

        items(note.data?.body ?: emptyList()) {
            it.Draw(modifier = Modifier)
        }
    }
}

@Composable
private fun ActionBar(
    noteTitle: String,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(text = noteTitle, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.weight(0.8f))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            IconButton(onClick = onEditClick) {
                Icon(Icons.Outlined.Edit, contentDescription = "edit")
            }
            IconButton(onClick = { }) {
                Icon(
                    painterResource(id = R.drawable.pin),
                    contentDescription = "pin",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "favorite")
            }
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "delete")
            }
        }


    }
}
