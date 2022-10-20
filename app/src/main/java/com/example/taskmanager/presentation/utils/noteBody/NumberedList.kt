package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@kotlinx.serialization.Serializable
class NumberedList(private val items: List<String>) : NoteBody {
    @Composable
    override fun Draw(modifier: Modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            itemsIndexed(items) { index, value ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${index + 1}.")
                    Text(text = value, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}