package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.dataModels.NoteWithTags
import kotlinx.coroutines.flow.Flow

fun interface GetNotesUseCase : suspend () -> Flow<List<NoteWithTags>>