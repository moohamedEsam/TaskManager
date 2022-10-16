package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.repository.Repository
import dev.krud.shapeshift.ShapeShift
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun interface GetNotesUseCase : suspend () -> Flow<List<NoteWithTagsDto>>

fun interface GetNoteByIdUseCase : suspend (String) -> Flow<NoteWithTagsDto?>

fun getNotesUseCase(repository: Repository, shapeShift: ShapeShift) = GetNotesUseCase {
    repository.getNotes().map { notes ->
        notes.map { note ->
            shapeShift.map(note)
        }
    }
}

fun getNoteByIdUseCase(repository: Repository, shapeShift: ShapeShift) = GetNoteByIdUseCase { id ->
    repository.getNoteById(id).map { note ->
        shapeShift.map(note ?: return@map null)
    }
}