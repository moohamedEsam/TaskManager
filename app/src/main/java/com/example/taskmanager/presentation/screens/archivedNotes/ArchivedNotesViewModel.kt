package com.example.taskmanager.presentation.screens.archivedNotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.SnackBarEvent
import com.example.taskmanager.domain.models.note.NoteWithTags
import com.example.taskmanager.domain.usecase.note.GetArchivedNotesUseCase
import com.example.taskmanager.domain.usecase.note.GetNotesUseCase
import com.example.taskmanager.domain.usecase.note.UpdateNoteArchiveUseCase
import com.example.taskmanager.presentation.utils.noteBody.NoteText
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ArchivedNotesViewModel(
    private val updateNoteArchiveUseCase: UpdateNoteArchiveUseCase,
    private val getArchivedNotesUseCase: GetArchivedNotesUseCase,
) : ViewModel() {
    private val _notes = MutableStateFlow(emptyList<NoteWithTags>())
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    val notes = combine(_notes, query) { notes, query ->
        notes.filter { note ->
            val noteBodyText = note.body.filterIsInstance<NoteText>()
            note.title.contains(query, true) || noteBodyText.any { it.text.contains(query, true) }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val snackBarChannel = Channel<SnackBarEvent>()
    val receiveChannel = snackBarChannel.receiveAsFlow()

    fun setQuery(value: String) = viewModelScope.launch {
        _query.update { value }
    }

    init {
        observeNotes()
    }

    private fun observeNotes() = viewModelScope.launch {
        getArchivedNotesUseCase().collectLatest {
            _notes.update { _ -> it }
        }
    }

    fun updateArchive(note: NoteWithTags): Job = viewModelScope.launch {
        val result = updateNoteArchiveUseCase(note.noteId, !note.isArchived)
        val event = if (result is Resource.Success)
            SnackBarEvent("Note UnArchived", "Undo") {
                updateNoteArchiveUseCase(note.noteId, note.isArchived)
            }
        else
            SnackBarEvent(result.message ?: "Error UnArchiving Note") { updateArchive(note) }
        snackBarChannel.send(event)
    }
}