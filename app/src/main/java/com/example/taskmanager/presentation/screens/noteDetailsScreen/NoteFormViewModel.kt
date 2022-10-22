package com.example.taskmanager.presentation.screens.noteDetailsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.interfaces.NoteWithTags
import com.example.taskmanager.domain.usecase.note.GetNoteByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
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
}