package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed interface NoteBody {
    @Composable
    fun Draw(modifier: Modifier)

    object Table
    object Video
    object Audio
    object File
}