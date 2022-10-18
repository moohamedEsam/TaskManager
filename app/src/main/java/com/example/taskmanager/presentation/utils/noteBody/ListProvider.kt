package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.taskmanager.presentation.composables.RemovableNoteBody
import com.example.taskmanager.presentation.utils.getTransparentTextFieldColors

class ListProvider : NoteBodyProvider {
    private var value = listOf("")

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Draw(modifier: Modifier, onRemove: () -> Unit) {
        val itemsCollection = remember {
            mutableStateListOf("")
        }
        LaunchedEffect(key1 = itemsCollection) {
            value = itemsCollection
        }
        RemovableNoteBody(onRemove = onRemove, modifier = modifier) {
            Column(modifier = it) {
                val title = listTitle()
                LazyColumn(
                    modifier = it
                        .fillMaxWidth()
                        .heightIn(20.dp, (LocalConfiguration.current.screenHeightDp / 3).dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(itemsCollection) { index, value ->
                        TextField(
                            value = value,
                            onValueChange = { itemsCollection[index] = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = getTransparentTextFieldColors()
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

    override fun getNoteBody(): NoteBody = DotList(value)

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
            colors = getTransparentTextFieldColors(),
            textStyle = MaterialTheme.typography.headlineMedium,
            singleLine = true,
            maxLines = 1
        )
        return value
    }
}
