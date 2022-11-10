package com.example.taskmanager.domain.usecase.tag

import com.example.taskmanager.domain.models.Resource
import com.example.taskmanager.domain.models.tag.Tag

fun interface CreateTagUseCase : suspend (Tag) -> Resource<Unit>
