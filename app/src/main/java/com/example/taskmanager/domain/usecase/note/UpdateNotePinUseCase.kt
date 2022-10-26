package com.example.taskmanager.domain.usecase.note

import com.example.taskmanager.domain.dataModels.Resource

fun interface UpdateNotePinUseCase : suspend (String, Boolean) -> Resource<Unit>
