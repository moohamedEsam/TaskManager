package com.example.taskmanager.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.taskmanager.domain.models.tag.Tag

@Composable
fun AddTagToNote(
    tags: List<Tag>,
    onDismiss: () -> Unit,
    onTagSelected: (Tag) -> Unit,
    onTagCreate: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card {
            CardContent(
                tags = tags,
                onTagCreate = {
                    onTagCreate(it)
                    onDismiss()
                },
                onTagSelected = {
                    onTagSelected(it)
                    onDismiss()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardContent(
    tags: List<Tag>,
    onTagCreate: (String) -> Unit,
    onTagSelected: (Tag) -> Unit
) {
    var query by remember {
        mutableStateOf("")
    }
    val suggestions by remember {
        derivedStateOf {
            if (query.isNotBlank())
                tags.filter { it.name.contains(query) }
            else
                tags
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxHeight(0.8f)) {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                if (suggestions.isEmpty() && query.isNotBlank()) {
                    item {
                        TextButton(onClick = { onTagCreate(query) }) {
                            Text("create $query")
                        }
                    }
                }
                items(suggestions) { tag ->
                    SuggestionChip(
                        onClick = { onTagSelected(tag) },
                        label = { Text(text = tag.name) })
                }
            }
        }
    }
}