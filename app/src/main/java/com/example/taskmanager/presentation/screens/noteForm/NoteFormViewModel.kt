package com.example.taskmanager.presentation.screens.noteForm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.usecase.note.CreateNoteUseCase
import com.example.taskmanager.domain.usecase.note.GetNoteByIdUseCase
import com.example.taskmanager.domain.usecase.note.UpdateNoteUseCase
import com.example.taskmanager.presentation.utils.noteBodyProvider.NoteBodyProvider
import com.example.taskmanager.presentation.utils.noteBody.NoteText
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

    init {
        getNote()
    }

    private fun getNote() = viewModelScope.launch {
        if (noteId.isNotBlank()) {
            getNoteByIdUseCase(noteId).collectLatest {
                if (it == null) return@collectLatest
                _note.update { _ -> it }
            }
        }
    }

    fun addNoteBody(noteBody: NoteBodyProvider) {
        _noteBodies.update { it + noteBody }
    }

    fun removeNoteBody(noteBody: NoteBodyProvider) {
        _noteBodies.update { it - noteBody }
    }

    fun saveNote() = viewModelScope.launch {
        _note.update {
            it.copy(
                body = _noteBodies.value.map { noteBody -> noteBody.getNoteBody() }
            )
        }
        val result = createNoteUseCase(_note.value)
        if (result is Resource.Error)
            Log.e("NoteFormViewModel", "saveNote: ${result.message}")
    }

    fun updateNoteTitle(value: String) {
        _note.update { it.copy(title = value) }
    }
}