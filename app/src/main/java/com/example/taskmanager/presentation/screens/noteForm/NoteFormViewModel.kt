package com.example.taskmanager.presentation.screens.noteForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.usecase.note.CreateNoteUseCase
import com.example.taskmanager.domain.usecase.note.GetNoteByIdUseCase
import com.example.taskmanager.domain.usecase.note.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteFormViewModel(
    private val noteId: String,
    private val createNoteUseCase: CreateNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : ViewModel() {
    private val _note = MutableStateFlow(NoteWithTagsDto())
    val note = _note.asStateFlow()

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
}