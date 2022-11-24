package com.example.taskmanager.presentation.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.SnackBarEvent
import com.example.taskmanager.domain.models.note.NoteWithTags
import com.example.taskmanager.domain.usecase.note.*
import com.example.taskmanager.presentation.utils.noteBody.NoteText
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
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
    private val snackbarChannel = Channel<SnackBarEvent>()
    val receiveChannel = snackbarChannel.receiveAsFlow()
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    val notes = combine(_notes, query) { notes, query ->
        notes.filter { note ->
            val noteBodyText = note.body.filterIsInstance<NoteText>()
            note.title.contains(query, true) || noteBodyText.any { it.text.contains(query, true) }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    init {
        observeNotes()
    }

    fun setQuery(value: String) = viewModelScope.launch {
        _query.update { value }
    }

    private fun observeNotes() = viewModelScope.launch {
        getNotesUseCase().collectLatest {
            _notes.value = it
        }
    }

    fun deleteNoteById(noteWithTags: NoteWithTags) = viewModelScope.launch {
        deleteNoteUseCase(noteWithTags)
    }

    fun updateArchive(noteWithTags: NoteWithTags): Job = viewModelScope.launch {
        val result = updateArchiveUseCase(noteWithTags.noteId, !noteWithTags.isArchived)
        val event = if (result is Resource.Success)
            SnackBarEvent("Note archived", "UNDO") {
                updateArchiveUseCase(noteWithTags.noteId, noteWithTags.isArchived)
            }
        else
            SnackBarEvent(result.message ?: "Error") { updateArchive(noteWithTags) }
        snackbarChannel.send(event)
    }

    fun updateFavorite(noteWithTags: NoteWithTags) = viewModelScope.launch {
        updateFavoriteUseCase(noteWithTags.noteId, !noteWithTags.isFavorite)
    }

    fun updatePin(noteWithTags: NoteWithTags) = viewModelScope.launch {
        updatePinUseCase(noteWithTags.noteId, !noteWithTags.isPinned)
    }

}