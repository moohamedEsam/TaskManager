package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.presentation.utils.getTransparentTextFieldColors
import com.example.taskmanager.presentation.utils.noteBodyProvider.NoteBodyProvider
import com.example.taskmanager.presentation.utils.noteBodyProvider.TableProvider
import com.example.taskmanager.ui.theme.TaskManagerTheme

@kotlinx.serialization.Serializable
class NoteTable(
    private val columns: Int,
    private val title: String,
    private val values: List<String>
) : NoteBody {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Draw(modifier: Modifier) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
            val columnState = rememberLazyListState()
            LazyRow(
                modifier = Modifier
                    .heightIn(80.dp, (LocalConfiguration.current.screenHeightDp / 3).dp)
            ) {
                items(columns) { columnIndex ->
                    LazyColumn(
                        state = columnState
                    ) {
                        items(values.size / columns) { it ->
                            val cellIndex = it * columns + columnIndex
                            TextField(
                                value = values[cellIndex],
                                onValueChange = {},
                                colors = getTransparentTextFieldColors(),
                                modifier = Modifier.border(1.dp, Color.LightGray),
                                textStyle = LocalTextStyle.current.copy(
                                    textDecoration =
                                    if (cellIndex < columns) TextDecoration.Underline
                                    else TextDecoration.None,
                                    fontWeight = if (cellIndex < columns) FontWeight.Bold
                                    else FontWeight.Normal
                                ),
                                readOnly = true,
                            )
                        }
                    }
                }
            }
        }
    }

    override fun getProvider(): NoteBodyProvider = TableProvider(columns, title, values)
}

@Preview
@Composable
fun TablePreview() {
    val table =
        NoteTable(3, "Table", listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"))
    TaskManagerTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            table.Draw(modifier = Modifier.fillMaxSize())
        }
    }
}