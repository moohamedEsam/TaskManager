package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.taskmanager.presentation.composables.RemovableNoteBody

object TextProvider : NoteBodyProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Draw(modifier: Modifier, onAdd: (NoteBody) -> Unit, onRemove: () -> Unit) {
        RemovableNoteBody(onRemove = onRemove, modifier = modifier) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = it.fillMaxSize()
            )
        }


    }
}