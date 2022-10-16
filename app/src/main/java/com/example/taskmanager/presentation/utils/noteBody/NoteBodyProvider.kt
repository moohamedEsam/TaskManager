package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed interface NoteBodyProvider {
    @Composable
    fun Draw(modifier: Modifier, onAdd: (NoteBody) -> Unit, onRemove: () -> Unit)
}