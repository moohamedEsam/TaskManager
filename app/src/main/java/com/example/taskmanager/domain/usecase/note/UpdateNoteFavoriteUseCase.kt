package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.models.Resource

fun interface UpdateNoteFavoriteUseCase : suspend (String, Boolean) -> Resource<Unit>
