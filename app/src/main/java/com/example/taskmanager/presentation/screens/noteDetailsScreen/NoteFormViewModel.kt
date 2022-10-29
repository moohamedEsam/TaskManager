package com.example.taskmanager.presentation.screens.noteDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.usecase.note.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteArchiveUseCase: UpdateNoteArchiveUseCase,
    private val updateNoteDeleteUseCase: UpdateNoteDeleteUseCase,
    private val updateNoteFavoriteUseCase: UpdateNoteFavoriteUseCase,
    private val updateNotePinUseCase: UpdateNotePinUseCase,
    noteId: String
) : ViewModel() {
    val note = getNoteByIdUseCase(noteId).map {
        if (it == null)
            Resource.Error("Note not found")
        else
            Resource.Success(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        Resource.Loading()
    )

    fun onArchiveClick() = viewModelScope.launch {
        if (note.value.data == null) return@launch
        updateNoteArchiveUseCase(note.value.data!!.noteId, !note.value.data!!.isArchived)
    }

    fun onDeleteClick() = viewModelScope.launch {
        if (note.value.data == null) return@launch
        updateNoteDeleteUseCase(note.value.data!!.noteId, !note.value.data!!.isDeleted)
    }

    fun onFavoriteClick() = viewModelScope.launch {
        if (note.value.data == null) return@launch
        updateNoteFavoriteUseCase(note.value.data!!.noteId, !note.value.data!!.isFavorite)
    }

    fun onPinClick() = viewModelScope.launch {
        if (note.value.data == null) return@launch
        updateNotePinUseCase(note.value.data!!.noteId, !note.value.data!!.isPinned)
    }

}