package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.models.NoteWithTags
import com.example.taskmanager.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun interface GetNotesUseCase : () -> Flow<List<NoteWithTags>>

fun interface GetNoteByIdUseCase : (String) -> Flow<NoteWithTags?>
