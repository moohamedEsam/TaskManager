package com.example.taskmanager.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.taskmanager.domain.models.tag.Tag
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TagDialog(
    showDialog: StateFlow<Boolean>,
    tagsState: StateFlow<List<Tag>>,
    onTagSelected: (Tag) -> Unit,
    onTagCreate: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val show by showDialog.collectAsState()
    val tags by tagsState.collectAsState()
    if (show)
        AddTagToNote(
            tags = tags,
            onTagSelected = onTagSelected,
            onTagCreate = onTagCreate,
            onDismiss = onDismiss
        )
}