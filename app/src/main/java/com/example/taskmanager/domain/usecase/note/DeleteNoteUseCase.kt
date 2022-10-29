package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.NoteWithTags
import com.example.taskmanager.domain.repository.NoteRepository

fun interface DeleteNoteUseCase : suspend (NoteWithTags) -> Resource<Unit>