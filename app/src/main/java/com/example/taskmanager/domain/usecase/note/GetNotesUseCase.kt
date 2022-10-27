package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun interface GetNotesUseCase : suspend () -> Flow<List<NoteWithTagsDto>>

fun interface GetNoteByIdUseCase : suspend (String) -> Flow<NoteWithTagsDto?>

fun getNotesUseCase(repository: Repository) = GetNotesUseCase {
    repository.getNotes().map { notes ->
        notes.map { note -> note.toDomain() }
    }
}

fun getNoteByIdUseCase(repository: Repository) = GetNoteByIdUseCase { id ->
    repository.getNoteById(id).map { note -> note?.toDomain() }
}