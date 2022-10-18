package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.taskmanager.presentation.composables.RemovableNoteBody
import com.example.taskmanager.presentation.utils.getTransparentTextFieldColors

class TextProvider : NoteBodyProvider {
    private var textState = mutableStateOf("")

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Draw(modifier: Modifier, onRemove: () -> Unit) {
        RemovableNoteBody(onRemove = onRemove, modifier = modifier) {
            val composeValue by remember {
                textState
            }
            TextField(
                value = composeValue,
                onValueChange = { newValue ->
                    textState.value = newValue
                },
                modifier = it.fillMaxSize(),
                colors = getTransparentTextFieldColors(),
                placeholder = { Text(text = "Enter text here") }
            )
        }


    }

    override fun getNoteBody(): NoteBody = NoteText(textState.value)
}