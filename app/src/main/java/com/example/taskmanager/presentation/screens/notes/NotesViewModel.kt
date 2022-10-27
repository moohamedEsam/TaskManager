package com.example.taskmanager.presentation.screens.notes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.usecase.note.*
import com.example.taskmanager.presentation.utils.noteBody.NoteImage
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateArchiveUseCase: UpdateNoteArchiveUseCase,
    private val updateFavoriteUseCase: UpdateNoteFavoriteUseCase,
    private val updatePinUseCase: UpdateNotePinUseCase
) : ViewModel() {
    private val _notes = MutableStateFlow(emptyList<NoteWithTagsDto>())
    val notes = _notes.asStateFlow()

    init {
        observeNotes()
    }

    private fun observeNotes() = viewModelScope.launch {
        getNotesUseCase().collectLatest {
            _notes.value = it
        }
    }

    fun deleteNoteById(noteWithTags: NoteWithTagsDto) = viewModelScope.launch {
        deleteNoteUseCase(noteWithTags)
    }

    fun updateArchive(noteWithTags: NoteWithTagsDto) = viewModelScope.launch {
        updateArchiveUseCase(noteWithTags.noteId, !noteWithTags.isArchived)
    }

    fun updateFavorite(noteWithTags: NoteWithTagsDto) = viewModelScope.launch {
        updateFavoriteUseCase(noteWithTags.noteId, !noteWithTags.isFavorite)
    }

    fun updatePin(noteWithTags: NoteWithTagsDto) = viewModelScope.launch {
        updatePinUseCase(noteWithTags.noteId, !noteWithTags.isPinned)
    }

}