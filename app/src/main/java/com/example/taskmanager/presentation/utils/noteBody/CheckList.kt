package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@kotlinx.serialization.Serializable
class CheckList(private val checkListItems: List<CheckListItem>) : NoteBody {
    @kotlinx.serialization.Serializable
    data class CheckListItem(val text: String, val isChecked: Boolean)

    @Composable
    override fun Draw(modifier: Modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            items(checkListItems) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = it.isChecked, onCheckedChange = {})
                    Text(
                        text = it.text,
                        modifier = Modifier.weight(1f),
                        textDecoration = if (it.isChecked) TextDecoration.LineThrough else TextDecoration.None
                    )
                }
            }
        }
    }
}