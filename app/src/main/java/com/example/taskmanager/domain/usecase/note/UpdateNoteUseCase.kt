package com.example.taskmanager.domain.usecase.note

import android.content.Context
import com.example.taskmanager.domain.dataModels.Resource
import com.example.taskmanager.domain.dataModels.presentation.NoteWithTagsDto
import com.example.taskmanager.domain.repository.Repository
import com.example.taskmanager.presentation.utils.noteBody.NoteMedia

fun interface UpdateNoteUseCase : suspend (NoteWithTagsDto) -> Resource<Unit>

fun updateNoteUseCase(context: Context, repository: Repository) =
    UpdateNoteUseCase { noteWithTags ->
        val containsMedia = noteWithTags.body.find { it is NoteMedia } != null
        if (!containsMedia)
            deleteNoteDir(context, noteWithTags)
        else
            updateNoteDirAndBody(
                context = context,
                noteWithTags = noteWithTags
            )

        repository.updateNote(noteWithTags.toEntity())
    }
