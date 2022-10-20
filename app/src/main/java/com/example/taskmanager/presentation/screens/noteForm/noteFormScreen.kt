package com.example.taskmanager.presentation.screens.noteForm

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.taskmanager.presentation.utils.getTransparentTextFieldColors
import com.example.taskmanager.presentation.utils.noteBodyProvider.*
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
    NoteFormScreenContent(
        viewModel = viewModel
    )
}

@Composable
fun NoteFormScreenContent(viewModel: NoteFormViewModel) {
    val noteBodies by viewModel.noteBodies.collectAsState()
    val showButtons by viewModel.showActionButtons.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
        ) {
            item {
                NoteTitle(viewModel)
            }
            items(noteBodies) { noteBody ->
                noteBody.Draw(modifier = Modifier) {
                    viewModel.removeNoteBody(noteBody)
                }
            }
            if (showButtons)
                item {
                    ActionRow {
                        viewModel.saveNote()
                    }
                }
        }
        ToolBoxRow(
            modifier = Modifier.align(Alignment.BottomCenter),
            onTitleClick = { viewModel.addNoteBody(TextProvider()) },
            onListClick = { viewModel.addNoteBody(ListProvider(ListType.Normal)) },
            onBulletListClick = { viewModel.addNoteBody(ListProvider(ListType.Bullet)) },
            onNumberedListClick = { viewModel.addNoteBody(ListProvider(ListType.Numbered)) },
            onCheckListClick = { viewModel.addNoteBody(ListProvider(ListType.Check)) },
            onTableClick = { viewModel.addNoteBody(TableProvider(6)) },
            onImageClick = { viewModel.addNoteBody(ImageProvider()) }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteTitle(viewModel: NoteFormViewModel) {
    val note by viewModel.note.collectAsState()
    val title by remember {
        derivedStateOf {
            note.title
        }
    }
    TextField(
        value = title,
        onValueChange = { viewModel.updateNoteTitle(it) },
        colors = getTransparentTextFieldColors(),
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Title",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
        },
        textStyle = LocalTextStyle.current.copy(fontSize = 24.sp)
    )
}

@Composable
private fun ActionRow(onSave: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
    ) {
        Button(onClick = onSave) {
            Text("Save")
        }
    }
}

@Composable
private fun ToolBoxRow(
    modifier: Modifier,
    onTitleClick: () -> Unit = {},
    onBulletListClick: () -> Unit = {},
    onNumberedListClick: () -> Unit = {},
    onCheckListClick: () -> Unit = {},
    onListClick: () -> Unit = {},
    onImageClick: () -> Unit = {},
    onVideoClick: () -> Unit = {},
    onTableClick: () -> Unit = {},
) {
    Card(modifier = modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.8f)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onTitleClick) {
                Icon(imageVector = Icons.Default.Title, contentDescription = null)
            }
            IconButton(onClick = onListClick) {
                Icon(imageVector = Icons.Default.List, contentDescription = null)
            }
            IconButton(onClick = onBulletListClick) {
                Icon(imageVector = Icons.Default.FormatListBulleted, contentDescription = null)
            }

            IconButton(onClick = onNumberedListClick) {
                Icon(imageVector = Icons.Default.FormatListNumbered, contentDescription = null)
            }
            IconButton(onClick = onCheckListClick) {
                Icon(imageVector = Icons.Default.Checklist, contentDescription = null)
            }
            IconButton(onClick = onImageClick) {
                Icon(imageVector = Icons.Default.Image, contentDescription = null)
            }
            IconButton(onClick = onVideoClick) {
                Icon(imageVector = Icons.Default.VideoFile, contentDescription = null)
            }
            IconButton(onClick = onTableClick) {
                Icon(imageVector = Icons.Default.TableView, contentDescription = null)
            }
            Spacer(modifier = Modifier.weight(0.8f))
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.ExpandLess, contentDescription = null)
            }
        }
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

            ToolBoxRow(modifier = Modifier.align(Alignment.BottomStart))
        }
    }
}