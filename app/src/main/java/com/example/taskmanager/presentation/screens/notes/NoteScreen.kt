package com.example.taskmanager.presentation.screens.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taskmanager.R
import com.example.taskmanager.domain.models.NoteWithTags
import com.example.taskmanager.domain.models.Tag
import com.example.taskmanager.presentation.utils.noteBody.NoteImage
import com.example.taskmanager.presentation.utils.noteBody.NoteText
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreen(
    onNoteClick: (String) -> Unit = {},
    viewModel: NotesViewModel = koinViewModel()
) {
    val notes by viewModel.notes.collectAsState()
    NotesScreen(
        notes = notes,
        onFavoriteClick = viewModel::updateFavorite,
        onPinClick = viewModel::updatePin,
        onNoteClick = onNoteClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    notes: List<NoteWithTags>,
    onFavoriteClick: (NoteWithTags) -> Unit,
    onPinClick: (NoteWithTags) -> Unit,
    onNoteClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Search Notes") },
            modifier = Modifier
                .fillMaxWidth()
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notes) { note ->
                TagCardItem(
                    onNoteClick = onNoteClick,
                    note = note,
                    onFavoriteClick = { onFavoriteClick(note) },
                    onPinClick = { onPinClick(note) }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TagCardItem(
    onNoteClick: (String) -> Unit,
    note: NoteWithTags,
    onFavoriteClick: () -> Unit = {},
    onPinClick: () -> Unit = {},
    onTagClick: (Tag) -> Unit = {}
) {
    OutlinedCard(
        onClick = { onNoteClick(note.noteId) },
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            note.body.find { it is NoteImage }?.Draw(
                Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp, (LocalConfiguration.current.screenHeightDp / 5).dp)
            )
            Row(
                modifier = Modifier.align(Alignment.TopEnd),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(onClick = onFavoriteClick) {
                    if (note.isFavorite)
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                    else
                        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
                }
                IconButton(onClick = onPinClick) {
                    Icon(
                        painter = if (note.isPinned)
                            painterResource(id = R.drawable.pin_filled)
                        else
                            painterResource(id = R.drawable.pin),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            note.body.find { it is NoteText }?.let {
                Text(
                    text = (it as NoteText).text,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
            TagsRow(note.tags, onTagClick = onTagClick)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Created: 12 Jan")
                Text("Note")
            }
        }
    }
}

@Composable
private fun TagsRow(tags: List<Tag>, onTagClick: (Tag) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        items(tags) { tag ->
            TextButton(
                content = { Text(tag.name) },
                onClick = { onTagClick(tag) }
            )
        }
    }
}