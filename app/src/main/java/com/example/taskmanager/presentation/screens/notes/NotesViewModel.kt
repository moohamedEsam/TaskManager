package com.example.taskmanager.presentation.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.models.NoteWithTags
import com.example.taskmanager.domain.usecase.note.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateArchiveUseCase: UpdateNoteArchiveUseCase,
    private val updateFavoriteUseCase: UpdateNoteFavoriteUseCase,
    private val updatePinUseCase: UpdateNotePinUseCase
) : ViewModel() {
    private val _notes = MutableStateFlow(emptyList<NoteWithTags>())
    val notes = _notes.asStateFlow()

    init {
        observeNotes()
    }

    private fun observeNotes() = viewModelScope.launch {
        getNotesUseCase().collectLatest {
            _notes.value = it
        }
    }

    fun deleteNoteById(noteWithTags: NoteWithTags) = viewModelScope.launch {
        deleteNoteUseCase(noteWithTags)
    }

    fun updateArchive(noteWithTags: NoteWithTags) = viewModelScope.launch {
        updateArchiveUseCase(noteWithTags.noteId, !noteWithTags.isArchived)
    }

    fun updateFavorite(noteWithTags: NoteWithTags) = viewModelScope.launch {
        updateFavoriteUseCase(noteWithTags.noteId, !noteWithTags.isFavorite)
    }

    fun updatePin(noteWithTags: NoteWithTags) = viewModelScope.launch {
        updatePinUseCase(noteWithTags.noteId, !noteWithTags.isPinned)
    }

}