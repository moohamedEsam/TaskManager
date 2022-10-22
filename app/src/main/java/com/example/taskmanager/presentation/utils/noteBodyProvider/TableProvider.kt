package com.example.taskmanager.presentation.utils.noteBodyProvider

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.taskmanager.presentation.composables.RemovableNoteBody
import com.example.taskmanager.presentation.utils.getTransparentTextFieldColors
import com.example.taskmanager.presentation.utils.noteBody.NoteBody
import com.example.taskmanager.presentation.utils.noteBody.NoteTable

class TableProvider(
    private val columns: Int,
    tableTitle: String = "",
    cells: List<String> = List(columns * 2) { "" }
) :
    NoteBodyProvider {
    private var cells = mutableStateListOf(*cells.toTypedArray())
    private val tableTitle = mutableStateOf(tableTitle)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Draw(modifier: Modifier, onRemove: () -> Unit) {
        RemovableNoteBody(
            onRemove = onRemove,
            modifier = modifier
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                val title by remember {
                    tableTitle
                }
                TextField(
                    value = title,
                    onValueChange = { tableTitle.value = it },
                    colors = getTransparentTextFieldColors(),
                    placeholder = { Text("Table title") },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )

                val columnState = rememberLazyListState()
                val horizontalFirstVisibleItemIndex = mutableStateOf(0)
                val horizontalFirstVisibleItemScrollOffset = mutableStateOf(0)
                if (columnState.isScrollInProgress) {
                    horizontalFirstVisibleItemIndex.value = columnState.firstVisibleItemIndex
                    horizontalFirstVisibleItemScrollOffset.value =
                        columnState.firstVisibleItemScrollOffset
                }
                LazyRow(
                    modifier = Modifier
                        .heightIn(80.dp, (LocalConfiguration.current.screenHeightDp / 3).dp),
                ) {
                    items(columns) { columnIndex ->
                        LazyColumn(
                            state = columnState,
                        ) {
                            items(cells.size / columns) { it ->
                                val focusManager = LocalFocusManager.current
                                val cellIndex = it * columns + columnIndex
                                TextField(
                                    value = cells[cellIndex],
                                    onValueChange = { cells[cellIndex] = it },
                                    colors = getTransparentTextFieldColors(),
                                    modifier = Modifier.border(1.dp, Color.LightGray),
                                    placeholder = {
                                        Text(
                                            if (cellIndex < columns)
                                                "Header ${cellIndex + 1}"
                                            else
                                                "Cell ${cellIndex + 1}"
                                        )
                                    },
                                    textStyle = LocalTextStyle.current.copy(
                                        textDecoration =
                                        if (cellIndex < columns) TextDecoration.Underline
                                        else TextDecoration.None,
                                        fontWeight = if (cellIndex < columns) FontWeight.Bold
                                        else FontWeight.Normal
                                    ),
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                    singleLine = true,
                                    keyboardActions = KeyboardActions(
                                        onNext = {
                                            if (cellIndex == cells.lastIndex)
                                                cells.addAll(Array(columns) { "" })
                                            focusManager.moveFocus(FocusDirection.Right)
                                        }
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getNoteBody(): NoteBody = NoteTable(columns, tableTitle.value, cells)

}