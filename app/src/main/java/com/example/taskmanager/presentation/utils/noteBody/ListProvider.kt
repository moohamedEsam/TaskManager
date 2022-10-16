package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskmanager.presentation.composables.RemovableNoteBody

object ListProvider : NoteBodyProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Draw(modifier: Modifier, onAdd: (NoteBody) -> Unit, onRemove: () -> Unit) {
        RemovableNoteBody(onRemove = onRemove, modifier = modifier) {
            val itemsCollection = remember {
                mutableStateListOf("")
            }
            Column(
                modifier = it.fillMaxWidth(),
            ) {
                val title = listTitle()
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(itemsCollection) { index, value ->
                        OutlinedTextField(
                            value = value,
                            onValueChange = { itemsCollection[index] = it },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
                OutlinedButton(
                    onClick = { itemsCollection.add("") },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Add Item")
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun listTitle(): String {
        var value by remember {
            mutableStateOf("")
        }
        TextField(
            value = value,
            onValueChange = { value = it },
            placeholder = {
                Text(
                    text = "List title",
                    style = MaterialTheme.typography.headlineMedium
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.headlineMedium,
            singleLine = true,
            maxLines = 1
        )
        return value
    }
}
