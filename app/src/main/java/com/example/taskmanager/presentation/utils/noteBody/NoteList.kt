package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@kotlinx.serialization.Serializable
class NoteList(private val items: List<String>) : NoteBody {
    @Composable
    override fun Draw(modifier: Modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            items(items) {
                Text(text = it, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}