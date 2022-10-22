package com.example.taskmanager.presentation.utils.noteBody

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.taskmanager.presentation.utils.noteBodyProvider.NoteBodyProvider
import com.example.taskmanager.presentation.utils.noteBodyProvider.TableProvider

@kotlinx.serialization.Serializable
class NoteTable(
    private val columns: Int,
    private val title: String,
    private val values: List<String>
) : NoteBody {
    @Composable
    override fun Draw(modifier: Modifier) {

    }

    override fun getProvider(): NoteBodyProvider = TableProvider(columns, title, values)
}