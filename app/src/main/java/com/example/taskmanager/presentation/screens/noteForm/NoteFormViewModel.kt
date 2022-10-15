package com.example.taskmanager.presentation.screens.noteForm

import com.example.taskmanager.domain.usecase.note.CreateNoteUseCase
import com.example.taskmanager.domain.usecase.note.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class NoteFormViewModel(
    private val noteId: String,
    private val createNoteUseCase: CreateNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) {

}