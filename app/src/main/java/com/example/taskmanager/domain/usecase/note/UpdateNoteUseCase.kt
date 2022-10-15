package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.dataModels.NoteWithTagsDto
import com.example.taskmanager.dataModels.Resource
import com.example.taskmanager.domain.dataModels.NoteWithTags
import com.example.taskmanager.domain.repository.Repository

fun interface UpdateNoteUseCase : suspend (NoteWithTags) -> Resource<Unit>

fun updateNoteUseCase(repository: Repository) = UpdateNoteUseCase {
    val noteWithTagsDto = it as? NoteWithTagsDto
        ?: return@UpdateNoteUseCase Resource.Error("NoteWithTags is not NoteWithTagsDto")
    repository.updateNote(noteWithTagsDto)
}