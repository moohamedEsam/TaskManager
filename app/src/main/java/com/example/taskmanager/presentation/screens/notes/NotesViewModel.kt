package com.example.taskmanager.presentation.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.dataModels.NoteWithTags
import com.example.taskmanager.domain.usecase.note.DeleteNoteUseCase
import com.example.taskmanager.domain.usecase.note.GetNotesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    private val _notes = MutableStateFlow(emptyList<NoteWithTags>())
    val notes = _notes.asStateFlow()

    init {
        observeNotes()
    }

    private fun observeNotes() = viewModelScope.launch {
        getNotesUseCase().collectLatest {
            _notes.update { _ -> it }
        }
    }

    fun deleteNoteById(noteWithTags: NoteWithTags) = viewModelScope.launch {
        deleteNoteUseCase(noteWithTags)
    }
}