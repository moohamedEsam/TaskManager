package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.dataModels.Resource

fun interface UpdateNoteArchiveUseCase : suspend (String, Boolean) -> Resource<Unit>
