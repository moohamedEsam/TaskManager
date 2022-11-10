package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.note.NoteWithTags

fun interface DeleteNoteUseCase : suspend (NoteWithTags) -> Resource<Unit>