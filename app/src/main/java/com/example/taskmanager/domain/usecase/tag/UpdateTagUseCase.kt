package com.example.taskmanager.domain.usecase.tag

import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.data.models.tag.TagEntity

fun interface UpdateTagUseCase : suspend (TagEntity) -> Resource<Unit>