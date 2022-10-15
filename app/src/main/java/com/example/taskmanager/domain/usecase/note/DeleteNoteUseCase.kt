package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.dataModels.NoteWithTagsDto
import com.example.taskmanager.dataModels.Resource
import com.example.taskmanager.domain.dataModels.NoteWithTags
import com.example.taskmanager.domain.repository.Repository

fun interface DeleteNoteUseCase : suspend (NoteWithTags) -> Resource<Unit>

fun deleteNoteUseCase(repository: Repository) = DeleteNoteUseCase { noteWithTags ->
    val noteWithTagsDto = noteWithTags as? NoteWithTagsDto
        ?: return@DeleteNoteUseCase Resource.Error("NoteWithTags is not NoteWithTagsDto")
    repository.deleteNote(noteWithTagsDto)
}