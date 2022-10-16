package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class NoteText(private val text: String) : NoteBody {
    @Composable
    override fun Draw(modifier: Modifier) {
        Text(text = text, modifier = modifier)
    }
}