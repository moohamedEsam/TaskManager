package com.example.taskmanager.presentation.screens.noteDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.interfaces.NoteWithTags
import com.example.taskmanager.domain.usecase.note.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteArchiveUseCase: UpdateNoteArchiveUseCase,
    private val updateNoteDeleteUseCase: UpdateNoteDeleteUseCase,
    private val updateNoteFavoriteUseCase: UpdateNoteFavoriteUseCase,
    private val updateNotePinUseCase: UpdateNotePinUseCase,
    private val noteId: String
) : ViewModel() {
    private val _note = MutableStateFlow<Resource<NoteWithTags>>(Resource.Initial())
    val note = _note.asStateFlow()

    init {
        observeNote()
    }

    private fun observeNote() {
        viewModelScope.launch {
            getNoteByIdUseCase(noteId).collectLatest {
                if (it == null)
                    _note.update { Resource.Error("note wasn't found") }
                else
                    _note.update { _ -> Resource.Success(it) }
            }
        }
    }

    fun onArchiveClick() = viewModelScope.launch {
        if (_note.value.data == null) return@launch
        updateNoteArchiveUseCase(_note.value.data!!.noteId, !_note.value.data!!.isArchived)
    }

    fun onDeleteClick() = viewModelScope.launch {
        if (_note.value.data == null) return@launch
        updateNoteDeleteUseCase(_note.value.data!!.noteId, !_note.value.data!!.isDeleted)
    }

    fun onFavoriteClick() = viewModelScope.launch {
        if (_note.value.data == null) return@launch
        updateNoteFavoriteUseCase(_note.value.data!!.noteId, !_note.value.data!!.isFavorite)
    }

    fun onPinClick() = viewModelScope.launch {
        if (_note.value.data == null) return@launch
        updateNotePinUseCase(_note.value.data!!.noteId, !_note.value.data!!.isPinned)
    }

}