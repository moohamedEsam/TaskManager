package com.example.taskmanager.presentation.screens.noteForm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.domain.models.tag.Tag
import com.example.taskmanager.presentation.composables.TagDialog
import com.example.taskmanager.presentation.composables.TagRow
import com.example.taskmanager.presentation.utils.getTransparentTextFieldColors
import com.example.taskmanager.presentation.utils.handleEvent
import com.example.taskmanager.presentation.utils.noteBodyProvider.*
import com.example.taskmanager.ui.theme.TaskManagerTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteFormScreen(
    snackbarHostState: SnackbarHostState,
    noteId: String,
    onNoteSaved: (noteId: String) -> Unit = {},
    viewModel: NoteFormViewModel = koinViewModel(parameters = { parametersOf(noteId) })
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.receiveChannel.collectLatest {
            snackbarHostState.handleEvent(it)
        }
    }
    val noteBodies by viewModel.noteBodies.collectAsState()
    val showSaveButton by viewModel.showActionButtons.collectAsState()
    val noteTags by viewModel.noteTags.collectAsState()

    NoteFormScreenContent(
        noteTitle = viewModel.noteTitle,
        noteBodies = noteBodies,
        showSaveButton = showSaveButton,
        onTitleValueChanged = { viewModel.updateNoteTitle(it) },
        onAddNoteBody = viewModel::addNoteBody,
        onRemoveNoteBody = viewModel::removeNoteBody,
        onToggleTagDialog = viewModel::toggleTagDialog,
        onSaveNote = { viewModel.saveNote(onNoteSaved) },
        noteTags = noteTags.toList(),
        onTagLongClick = viewModel::removeTag,
        onUpClick = viewModel::moveNoteBodyUp,
        onDownClick = viewModel::moveNoteBodyDown,
    )

    TagDialog(
        showDialog = viewModel.showTagDialog,
        tagsState = viewModel.tags,
        onTagSelected = viewModel::addTag,
        onTagCreate = viewModel::createTag,
        onDismiss = viewModel::toggleTagDialog
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteFormScreenContent(
    noteTitle: StateFlow<String>,
    noteBodies: List<NoteBodyProvider>,
    noteTags: List<Tag>,
    showSaveButton: Boolean,
    onTitleValueChanged: (String) -> Unit,
    onAddNoteBody: (NoteBodyProvider) -> Unit,
    onRemoveNoteBody: (NoteBodyProvider) -> Unit,
    onUpClick: (NoteBodyProvider) -> Unit,
    onDownClick: (NoteBodyProvider) -> Unit,
    onToggleTagDialog: () -> Unit,
    onTagLongClick: (Tag) -> Unit,
    onSaveNote: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
        ) {
            item {
                NoteTitle(
                    title = noteTitle,
                    onTitleValueChanged = onTitleValueChanged
                )
            }
            item {
                TagRow(
                    tags = noteTags,
                    onToggleTagDialog = onToggleTagDialog
                )
            }
            items(noteBodies, key = { it.id }) { noteBody ->
                noteBody.Draw(
                    modifier = Modifier.animateItemPlacement(),
                    onRemove = { onRemoveNoteBody(noteBody) },
                    onUpClick = { onUpClick(noteBody) },
                    onDownClick = { onDownClick(noteBody) }
                )
            }
            item {
                AnimatedVisibility(
                    visible = showSaveButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    ActionRow(onSave = onSaveNote)
                }
            }
        }
        ToolBoxRow(
            modifier = Modifier.align(Alignment.BottomCenter),
            onTitleClick = { onAddNoteBody(TextProvider()) },
            onListClick = { onAddNoteBody(ListProvider(ListType.Normal)) },
            onBulletListClick = { onAddNoteBody(ListProvider(ListType.Bullet)) },
            onNumberedListClick = { onAddNoteBody(ListProvider(ListType.Numbered)) },
            onCheckListClick = { onAddNoteBody(ListProvider(ListType.Check)) },
            onTableClick = { onAddNoteBody(TableProvider()) },
            onImageClick = { onAddNoteBody(ImageProvider()) },
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteTitle(title: StateFlow<String>, onTitleValueChanged: (String) -> Unit) {
    val value by title.collectAsState()
    TextField(
        value = value,
        onValueChange = onTitleValueChanged,
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
            NoteFormScreenContent(
                noteTitle = MutableStateFlow("test"),
                noteBodies = listOf(TextProvider()),
                showSaveButton = false,
                onTitleValueChanged = {},
                onAddNoteBody = {},
                onRemoveNoteBody = {},
                onToggleTagDialog = { },
                noteTags = listOf(Tag("test")),
                onTagLongClick = {},
                onUpClick = {},
                onDownClick = {},
            ) {

            }
        }
    }
}