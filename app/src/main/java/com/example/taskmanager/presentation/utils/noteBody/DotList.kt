package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

class DotList(private val items: List<String>) : NoteBody {
        @Composable
        override fun Draw(modifier: Modifier) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = modifier.fillMaxWidth()
            ) {
                items(items) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = null,
                            modifier = Modifier.scale(0.5f)
                        )
                        Text(text = it, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
}