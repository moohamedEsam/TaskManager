package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.models.note.NoteWithTags
import kotlinx.coroutines.flow.Flow

fun interface GetArchivedNotesUseCase : () -> Flow<List<NoteWithTags>>