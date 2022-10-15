package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.dataModels.NoteWithTagsDto
import com.example.taskmanager.dataModels.Resource
import com.example.taskmanager.domain.dataModels.NoteWithTags
import com.example.taskmanager.domain.repository.Repository

fun interface CreateNoteUseCase : suspend (NoteWithTags) -> Resource<Unit>

fun createNoteUseCase(repository: Repository) = CreateNoteUseCase {
    val noteWithTagsDto = it as? NoteWithTagsDto
        ?: return@CreateNoteUseCase Resource.Error("NoteWithTags is not NoteWithTagsDto")
    repository.addNote(noteWithTagsDto)
}