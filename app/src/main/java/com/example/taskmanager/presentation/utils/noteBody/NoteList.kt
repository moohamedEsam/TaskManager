package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.taskmanager.presentation.utils.noteBodyProvider.ListProvider
import com.example.taskmanager.presentation.utils.noteBodyProvider.ListType
import com.example.taskmanager.presentation.utils.noteBodyProvider.NoteBodyProvider

@kotlinx.serialization.Serializable
class NoteList(
    private val listType: ListType,
    private val title: String,
    private val items: List<String>
) : NoteBody {
    @Composable
    override fun Draw(modifier: Modifier) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(40.dp, (LocalConfiguration.current.screenHeightDp / 3).dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp)
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

    override fun getProvider(): NoteBodyProvider = ListProvider(
        listType = listType,
        listTitle = title,
        collection = items
    )
}