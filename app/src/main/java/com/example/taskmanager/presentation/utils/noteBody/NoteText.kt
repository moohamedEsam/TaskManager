package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.taskmanager.presentation.utils.noteBodyProvider.NoteBodyProvider
import com.example.taskmanager.presentation.utils.noteBodyProvider.TextProvider

@kotlinx.serialization.Serializable
class NoteText(val text: String) : NoteBody {
    @Composable
    override fun Draw(modifier: Modifier) {
        Text(text = text, modifier = modifier)
    }

    override fun getProvider(): NoteBodyProvider = TextProvider(text)
}