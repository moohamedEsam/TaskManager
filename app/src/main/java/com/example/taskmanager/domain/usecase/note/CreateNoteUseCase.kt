package com.example.taskmanager.domain.usecase.note

import android.content.Context
import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.NoteWithTags
import com.example.taskmanager.domain.models.asEntity
import com.example.taskmanager.domain.repository.NoteRepository
import com.example.taskmanager.presentation.utils.noteBody.NoteMedia

fun interface CreateNoteUseCase : suspend (NoteWithTags) -> Resource<Unit>

fun createNoteUseCase(
    context: Context,
    noteRepository: NoteRepository
) =
    CreateNoteUseCase { noteWithTags ->
        val containMedia = noteWithTags.body.find { it is NoteMedia } != null
        if (containMedia)
            updateNoteDirAndBody(context, noteWithTags)
        noteRepository.addNote(noteWithTags)
    }