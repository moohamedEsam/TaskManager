package com.example.taskmanager.presentation.screens.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.R
import com.example.taskmanager.domain.models.note.NoteWithTags
import com.example.taskmanager.domain.models.tag.Tag
import com.example.taskmanager.presentation.utils.noteBody.NoteImage
import com.example.taskmanager.presentation.utils.noteBody.NoteText
import com.example.taskmanager.ui.theme.TaskManagerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreenRoute(
    onNoteClick: (String) -> Unit = {},
    viewModel: NotesViewModel = koinViewModel()
) {
    val notes by viewModel.notes.collectAsState()
    NotesScreenRoute(
        notes = notes,
        onFavoriteClick = viewModel::updateFavorite,
        onPinClick = viewModel::updatePin,
        onNoteClick = onNoteClick
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NotesScreenRoute(
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
            items(notes, key = { it.noteId }) { note ->
                NoteCardItem(
                    onNoteClick = onNoteClick,
                    note = note,
                    onFavoriteClick = { onFavoriteClick(note) },
                    onPinClick = { onPinClick(note) },
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteCardItem(
    onNoteClick: (String) -> Unit,
    note: NoteWithTags,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit = {},
    onPinClick: () -> Unit = {},
    onTagClick: (Tag) -> Unit = {}
) {
    OutlinedCard(
        onClick = { onNoteClick(note.noteId) },
        modifier = modifier
            .height((LocalConfiguration.current.screenHeightDp / 3).dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                note.body.find { it is NoteImage }?.Draw(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(40.dp, (LocalConfiguration.current.screenHeightDp / 5).dp)
                )
                NoteCardIconButtons(
                    note = note,
                    modifier = Modifier.align(Alignment.TopEnd),
                    onFavoriteClick = onFavoriteClick,
                    onPinClick = onPinClick
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.headlineMedium,
                )
                note.body.find { it is NoteText }?.let {
                    Text(
                        text = (it as NoteText).text,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2
                    )
                }
                TagsRow(note.tags, onTagClick = onTagClick)
                Spacer(modifier = Modifier.weight(0.8f))
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
}

@Composable
private fun NoteCardIconButtons(
    note: NoteWithTags,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit,
    onPinClick: () -> Unit
) {
    Row(
        modifier = modifier,
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

@Preview(showSystemUi = true)
@Composable
fun LazyGridPreview() {
    TaskManagerTheme {
        NotesScreenRoute(
            notes = listOf(
                NoteWithTags("first note", emptyList(), emptyList(), emptyList()),
                NoteWithTags("second note", emptyList(), emptyList(), emptyList()),
                NoteWithTags("third note", emptyList(), emptyList(), emptyList()),
                NoteWithTags("fourth note", listOf(NoteText("hi there")), emptyList(), emptyList()),
                NoteWithTags("fifth note", emptyList(), emptyList(), emptyList()),
            ),
            onFavoriteClick = {},
            onPinClick = {},
            onNoteClick = {}
        )
    }
}