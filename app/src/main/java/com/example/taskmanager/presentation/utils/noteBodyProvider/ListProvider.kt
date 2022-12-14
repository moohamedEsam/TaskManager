package com.example.taskmanager.presentation.utils.noteBodyProvider

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.taskmanager.presentation.composables.RemovableNoteBody
import com.example.taskmanager.presentation.utils.getTransparentTextFieldColors
import com.example.taskmanager.presentation.utils.noteBody.*
import java.util.*

class ListProvider(
    private val listType: ListType,
    listTitle: String = "",
    collection: List<String> = listOf("")
) : NoteBodyProvider {

    override val id: String = UUID.randomUUID().toString()
    private var collection = mutableStateListOf(*collection.toTypedArray())
    private val listTitle = mutableStateOf(listTitle)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Draw(
        modifier: Modifier,
        onRemove: () -> Unit,
        onUpClick: () -> Unit,
        onDownClick: () -> Unit
    ) {
        RemovableNoteBody(
            onRemove = onRemove,
            modifier = modifier,
            onUpClick = onUpClick,
            onDownClick = onDownClick
        ) {
            Column(modifier = it) {
                ListTitle()
                LazyColumn(
                    modifier = it
                        .fillMaxWidth()
                        .padding(8.dp)
                        .heightIn(20.dp, (LocalConfiguration.current.screenHeightDp / 3).dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(collection) { index, value ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LeadingIcon(index)
                            TextField(
                                value = value,
                                onValueChange = { collection[index] = it },
                                modifier = Modifier.fillMaxWidth(),
                                colors = getTransparentTextFieldColors(),
                                placeholder = { Text(text = "List Item ${index + 1}") }
                            )
                        }
                    }

                }
                OutlinedButton(
                    onClick = { collection.add("") },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Add Item")
                }

            }

        }
    }

    @Composable
    private fun LeadingIcon(index: Int) {
        when (listType) {
            ListType.Bullet -> {
                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = null,
                    modifier = Modifier.scale(0.5f)
                )
            }
            ListType.Numbered -> {
                Text(text = "${index + 1}.")
            }
            ListType.Check -> {
                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    enabled = false
                )
            }
            else -> Unit
        }
    }

    override fun getNoteBody(): NoteBody = NoteList(
        listType = listType,
        title = listTitle.value,
        items = collection
    )


    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun ListTitle(): String {
        val value by remember {
            listTitle
        }
        TextField(
            value = value,
            onValueChange = { listTitle.value = it },
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

enum class ListType {
    Bullet, Numbered, Check, Normal
}