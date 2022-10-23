package com.example.taskmanager.presentation.screens.noteForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.SnackBarEvent
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.usecase.note.CreateNoteUseCase
import com.example.taskmanager.domain.usecase.note.GetNoteByIdUseCase
import com.example.taskmanager.domain.usecase.note.UpdateNoteUseCase
import com.example.taskmanager.presentation.utils.noteBodyProvider.NoteBodyProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteFormViewModel(
    private val noteId: String,
    private val createNoteUseCase: CreateNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : ViewModel() {
    private val _note = MutableStateFlow(NoteWithTagsDto())
    val note = _note.asStateFlow()
    private val _noteBodies = MutableStateFlow(emptyList<NoteBodyProvider>())
    val noteBodies = _noteBodies.asStateFlow()
    val showActionButtons =
        _noteBodies.map { it.isNotEmpty() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val snackBarChannel = Channel<SnackBarEvent>()
    val receiveChannel = snackBarChannel.receiveAsFlow()

    init {
        getNote()
    }

    private fun getNote() = viewModelScope.launch {
        if (noteId.isNotBlank()) {
            getNoteByIdUseCase(noteId).collectLatest { noteWithTagsDto ->
                if (noteWithTagsDto == null) return@collectLatest
                _note.update { _ -> noteWithTagsDto }
                _noteBodies.update { _ -> noteWithTagsDto.body.map { it.getProvider() } }
            }
        }
    }

    fun addNoteBody(noteBody: NoteBodyProvider) {
        _noteBodies.update { it + noteBody }
    }

    fun removeNoteBody(noteBody: NoteBodyProvider) {
        _noteBodies.update { it - noteBody }
    }

    fun saveNote(): Job = viewModelScope.launch {
        _note.update {
            it.copy(
                body = _noteBodies.value.map { noteBody -> noteBody.getNoteBody() }
            )
        }
        val result = if (noteId.isBlank())
            createNoteUseCase(_note.value)
        else
            updateNoteUseCase(_note.value)
        val event = when (result) {
            is Resource.Error -> SnackBarEvent(result.message ?: "") { saveNote() }
            is Resource.Success -> SnackBarEvent("note has been saved", null)
            else -> null
        }
        if (event != null)
            snackBarChannel.send(event)
    }

    fun updateNoteTitle(value: String) {
        _note.update { it.copy(title = value) }
    }
}