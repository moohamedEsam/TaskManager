package com.example.taskmanager.domain.usecase.note

import android.content.Context
import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.repository.Repository
import com.example.taskmanager.presentation.utils.noteBody.NoteMedia

fun interface CreateNoteUseCase : suspend (NoteWithTagsDto) -> Resource<Unit>

fun createNoteUseCase(
    context: Context,
    repository: Repository
) =
    CreateNoteUseCase { noteWithTags ->
        val containMedia = noteWithTags.body.find { it is NoteMedia } != null
        if (containMedia)
            updateNoteDirAndBody(context, noteWithTags)
        repository.addNote(noteWithTags.toEntity())
    }