package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.repository.Repository

fun interface DeleteNoteUseCase : suspend (NoteWithTagsDto) -> Resource<Unit>

fun deleteNoteUseCase(repository: Repository) = DeleteNoteUseCase {
    repository.deleteNote(it.toEntity())
}