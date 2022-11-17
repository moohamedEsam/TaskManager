package com.example.taskmanager.presentation.utils.noteBodyProvider

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.taskmanager.presentation.utils.noteBody.NoteBody

sealed interface NoteBodyProvider {
    val id: String;

    @Composable
    fun Draw(
        modifier: Modifier,
        onRemove: () -> Unit,
        onUpClick: () -> Unit,
        onDownClick: () -> Unit
    )

    fun getNoteBody(): NoteBody
}