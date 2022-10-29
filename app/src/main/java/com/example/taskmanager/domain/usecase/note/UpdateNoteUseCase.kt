package com.example.taskmanager.domain.usecase.note

import android.content.Context
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.NoteWithTags
import com.example.taskmanager.domain.models.asEntity
import com.example.taskmanager.domain.repository.NoteRepository
import com.example.taskmanager.presentation.utils.noteBody.NoteMedia

fun interface UpdateNoteUseCase : suspend (NoteWithTags) -> Resource<Unit>

fun updateNoteUseCase(context: Context, noteRepository: NoteRepository) =
    UpdateNoteUseCase { noteWithTags ->
        val containsMedia = noteWithTags.body.find { it is NoteMedia } != null
        if (!containsMedia)
            deleteNoteDir(context, noteWithTags)
        else
            updateNoteDirAndBody(
                context = context,
                noteWithTags = noteWithTags
            )

        noteRepository.updateNote(noteWithTags)
    }
