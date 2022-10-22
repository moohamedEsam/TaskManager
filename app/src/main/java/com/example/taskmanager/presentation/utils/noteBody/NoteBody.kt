package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.taskmanager.presentation.utils.noteBodyProvider.NoteBodyProvider

@kotlinx.serialization.Serializable
sealed interface NoteBody {
    @Composable
    fun Draw(modifier: Modifier)

    fun getProvider(): NoteBodyProvider
}